package at.renehollander.photosofinterest.challenge

import android.graphics.Bitmap
import android.net.Uri
import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.challenge.domain.usecase.CreatePost
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.source.UserManager
import at.renehollander.photosofinterest.feed.domain.model.RequestFilter
import at.renehollander.photosofinterest.feed.domain.usecase.LoadPosts
import com.google.firebase.firestore.GeoPoint
import java.io.File
import javax.inject.Inject

class ChallengePresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler,
        private val loadPosts: LoadPosts,
        private val createPost: CreatePost,
        private val userManager: UserManager
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
        if (this.userManager.isLoggedIn()) {
            this.view?.startPhotoTake()
        } else {
            this.view?.startLogin()
        }
    }

    override fun photoTaken(photo: Bitmap) {
    }

    override fun createPost(title: String, image: String, origin: GeoPoint) {
        if (this.challenge == null) return

        this.useCaseHandler.execute(this.createPost,
                CreatePost.RequestValues(this.challenge!!, title, image, origin), { response ->
            view?.onPostCreation(response.post)
        }, {
            view?.onPostCreationFailed()
        })
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