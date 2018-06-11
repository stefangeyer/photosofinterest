package at.renehollander.photosofinterest.challenge

import android.graphics.Bitmap
import at.renehollander.photosofinterest.auth.SignInEvent
import at.renehollander.photosofinterest.auth.SignOutEvent
import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.User
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class ChallengePresenter @Inject constructor(
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

    override fun update() {
        this.view?.getDetailsPresenter()?.setChallenge(challenge)
        this.view?.getDetailsPresenter()?.update()
    }
}