package at.renehollander.photosofinterest.challenge

import android.graphics.Bitmap
import android.util.Log
import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.challenges.domain.usecase.LoadChallenges
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.data.source.LoadRecordCallback
import at.renehollander.photosofinterest.data.source.PostDataRepository
import at.renehollander.photosofinterest.data.source.UserManager
import at.renehollander.photosofinterest.feed.domain.usecase.LoadPosts
import javax.inject.Inject

class ChallengePresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler,
        private val loadPosts: LoadPosts,
        private val userManager: UserManager,
        private val postDataRepository: PostDataRepository
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

    override fun setChallenge(challenge: Challenge?) {
        this.challenge = challenge
        this.view?.getDetailsPresenter()?.setChallenge(challenge)
    }

    override fun loadChallengePosts() {
        postDataRepository.loadPosts(challenge!!, object : LoadRecordCallback<Post> {
            override fun onRecordsLoaded(records: List<Post>) {
                view?.updateChallengePosts(records)
            }

            override fun onDataNotAvailable() {
                Log.d(LoadChallenges.TAG, "Fetching challenge did not produce any data")
            }
        })
    }

    override fun update() {
        this.view?.getDetailsPresenter()?.setChallenge(challenge)
    }
}