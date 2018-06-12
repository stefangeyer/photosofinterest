package at.renehollander.photosofinterest.main

import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.data.User

/**
 * Contract for the main parentView
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface MainContract {

    interface View : BaseView {
        fun startSignIn()
        fun startSignOut()
        fun onSignIn(user: User)
        fun onSignOut()
    }

    interface Presenter : BasePresenter<View> {
        fun signIn()
        fun signOut()
        fun getUser(): User?
        fun enableAuthEvents()
        fun disableAuthEvents()
    }
}
