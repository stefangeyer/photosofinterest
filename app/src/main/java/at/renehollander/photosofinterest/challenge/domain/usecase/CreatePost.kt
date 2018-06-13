package at.renehollander.photosofinterest.challenge.domain.usecase

import at.renehollander.photosofinterest.UseCase
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.data.source.GetRecordCallback
import at.renehollander.photosofinterest.data.source.PostDataSource
import com.google.firebase.firestore.GeoPoint
import javax.inject.Inject

class CreatePost @Inject constructor(
        private val postDataSource: PostDataSource
) : UseCase<CreatePost.RequestValues, CreatePost.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues == null) {
            useCaseCallback?.onError()
            return
        }

        this.postDataSource.createPost(requestValues.challenge, requestValues.title,
                requestValues.image, requestValues.origin, object : GetRecordCallback<Post> {
            override fun onRecordLoaded(record: Post) {
                useCaseCallback?.onSuccess(ResponseValue(record))
            }

            override fun onDataNotAvailable() {
                useCaseCallback?.onError()
            }
        })
    }

    class RequestValues(val challenge: Challenge, val title: String,
                        val image: String, val origin: GeoPoint) : UseCase.RequestValues

    class ResponseValue(val post: Post) : UseCase.ResponseValue
}