package at.renehollander.photosofinterest.challenge

import android.graphics.Bitmap
import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.feed.domain.model.RequestFilter
import at.renehollander.photosofinterest.feed.domain.usecase.LoadPosts
import javax.inject.Inject

class ChallengePresenter @Inject constructor(
        private val application: PhotosOfInterest,
        private val useCaseHandler: UseCaseHandler,
        private val loadPosts: LoadPosts
) : ChallengeContract.Presenter {

    private var view: ChallengeContract.View? = null

    private var challenge: Challenge? = null

    override fun takeView(view: ChallengeContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun takePhoto() {
        if (application.isLoggedIn()) {
            this.view?.startPhotoTake()
        } else {
            this.view?.startLogin()
        }
    }

    override fun photoTaken(photo: Bitmap) {
    }

    override fun setChallenge(challenge: Challenge?) {
        this.challenge = challenge
        this.view?.getDetailsPresenter()?.setChallenge(challenge)
    }

    override fun loadChallengePosts() {
        this.useCaseHandler.execute(this.loadPosts, LoadPosts.RequestValues(RequestFilter.TOP), {
            // Success
            response ->
            this.view?.updateChallengePosts(response.posts)
        }, {
            // Failure
            this.view?.showCannotReload()
        })
    }

    override fun update() {
        this.view?.getDetailsPresenter()?.setChallenge(challenge)
    }
}