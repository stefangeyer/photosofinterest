package at.renehollander.photosofinterest.main

import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.data.source.UserManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val userManager: UserManager
) : MainContract.Presenter {

    private var view: MainContract.View? = null

    override fun takeView(view: MainContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPerformSignInEvent(event: PerformSignInEvent) {
        if (this.userManager.getCurrentUser() == null) {
            this.view?.startSignIn()
        }
    }

    override fun signIn() {
        this.view?.startSignIn()
    }

    override fun signOut() {
        this.userManager.logout()
        this.view?.startSignOut()
    }

    override fun getUser(): User? {
        return this.userManager.getCurrentUser()
    }
}
