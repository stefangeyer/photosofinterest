package at.renehollander.photosofinterest.feed.domain.usecase

import android.util.Log
import at.renehollander.photosofinterest.UseCase
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.data.source.PostDataSource
import javax.inject.Inject

class LoadPosts @Inject constructor(
        private val dataSource: PostDataSource
) : UseCase<LoadPosts.RequestValues, LoadPosts.ResponseValue>() {

    companion object {
        const val TAG = "LoadPosts"
    }

    override fun executeUseCase(requestValues: RequestValues?) {
        Log.d(TAG, "Fetching posts from remote")

        this.dataSource.loadPosts(object : PostDataSource.LoadRecordCallback<Post> {
            override fun onRecordsLoaded(records: List<Post>) {
                this@LoadPosts.useCaseCallback?.onSuccess(ResponseValue(records))
                Log.d(TAG, "Fetching posts was successful")
            }

            override fun onDataNotAvailable() {
                this@LoadPosts.useCaseCallback?.onError()
                Log.d(TAG, "Fetching posts did not produce any data")
            }
        })
    }

    class RequestValues : UseCase.RequestValues

    class ResponseValue(val posts: List<Post>) : UseCase.ResponseValue
}