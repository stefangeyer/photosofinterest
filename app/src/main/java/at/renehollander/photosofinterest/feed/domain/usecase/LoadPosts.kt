package at.renehollander.photosofinterest.feed.domain.usecase

import android.util.Log
import at.renehollander.photosofinterest.UseCase
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.data.source.LoadRecordCallback
import at.renehollander.photosofinterest.data.source.PostDataSource
import at.renehollander.photosofinterest.data.source.PostDataSource.Filter
import at.renehollander.photosofinterest.feed.domain.model.RequestFilter
import javax.inject.Inject

class LoadPosts @Inject constructor(
        private val dataSource: PostDataSource
) : UseCase<LoadPosts.RequestValues, LoadPosts.ResponseValue>() {

    companion object {
        const val TAG = "LoadPosts"
    }

    override fun executeUseCase(requestValues: RequestValues?) {
        Log.d(TAG, "Fetching challenges from remote")

        val requestFilter = requestValues?.filter
        var remoteFilter: Filter = Filter.RISING

        when (requestFilter) {
            RequestFilter.RISING -> remoteFilter = Filter.RISING
            RequestFilter.RECENT -> remoteFilter = Filter.RECENT
            RequestFilter.TOP -> remoteFilter = Filter.TOP
        }

        this.dataSource.loadPosts(remoteFilter, object : LoadRecordCallback<Post> {
            override fun onRecordsLoaded(records: List<Post>) {
                this@LoadPosts.useCaseCallback?.onSuccess(ResponseValue(requestFilter, records))
                Log.d(TAG, "Fetching challenges was successful")
            }

            override fun onDataNotAvailable() {
                this@LoadPosts.useCaseCallback?.onError()
                Log.d(TAG, "Fetching challenges did not produce any data")
            }
        })
    }

    class RequestValues(val filter: RequestFilter) : UseCase.RequestValues

    class ResponseValue(val filter: RequestFilter?, val posts: List<Post>) : UseCase.ResponseValue
}