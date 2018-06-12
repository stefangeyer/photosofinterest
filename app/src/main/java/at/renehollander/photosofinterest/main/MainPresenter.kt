package at.renehollander.photosofinterest.main

import at.renehollander.photosofinterest.UseCase
import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.auth.SignInEvent
import at.renehollander.photosofinterest.auth.SignOutEvent
import at.renehollander.photosofinterest.auth.domain.usecase.ChangeAuthStateListener
import at.renehollander.photosofinterest.auth.domain.usecase.ChangeAuthStateListener.Action
import at.renehollander.photosofinterest.auth.domain.usecase.ChangeAuthStateListener.RequestValues
import at.renehollander.photosofinterest.auth.domain.usecase.SignOut
import at.renehollander.photosofinterest.data.User
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class MainPresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler,
        private val changeAuthStateListener: ChangeAuthStateListener,
        private val signOut: SignOut
) : MainContract.Presenter {

    private var view: MainContract.View? = null
    private var user: User? = null

    override fun takeView(view: MainContract.View) {
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
        this.view?.onSignIn(event.user)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLogoutEvent(event: SignOutEvent) {
        this.user = null
        this.view?.onSignOut()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPerformSignInEvent(event: PerformSignInEvent) {
        if (this.user == null) {
            this.view?.startSignIn()
        }
    }

    override fun enableAuthEvents() {
        this.useCaseHandler.execute(
                this.changeAuthStateListener,
                RequestValues(
                        Action.ADD,
                        { user ->
                            // Update user display
                            EventBus.getDefault().post(SignInEvent(user))
                        },
                        {
                            EventBus.getDefault().post(SignOutEvent())
                        }),
                {}, {})
    }

    override fun disableAuthEvents() {
        this.useCaseHandler.execute(
                this.changeAuthStateListener,
                RequestValues(Action.REMOVE, null, null),
                {}, {})
    }

    override fun signIn() {
        this.view?.startSignIn()
    }

    override fun signOut() {
        this.useCaseHandler.execute(signOut, object : UseCase.RequestValues {}, {}, {})
        this.view?.startSignOut()
    }

    override fun getUser(): User? {
        return this.user
    }
}
