package at.renehollander.photosofinterest.challenge

import android.graphics.Bitmap
import at.renehollander.photosofinterest.auth.SignInEvent
import at.renehollander.photosofinterest.auth.SignOutEvent
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.User
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.feed.domain.model.RequestFilter
import at.renehollander.photosofinterest.feed.domain.usecase.LoadPosts
import javax.inject.Inject

class ChallengePresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler,
        private val loadPosts: LoadPosts
) : ChallengeContract.Presenter {

    private var view: ChallengeContract.View? = null

    private var challenge: Challenge? = null
    private var user: User? = null

    override fun takeView(view: ChallengeContract.View) {
        this.view = view
        EventBus.getDefault().register(this)
    }

    override fun dropView() {
        this.view = null
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event: SignInEvent) {
        this.user = event.user
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogoutEvent(event: SignOutEvent) {
        this.user = null
    }

    override fun takePhoto() {
        if (this.user != null) {
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