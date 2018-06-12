package at.renehollander.photosofinterest.auth

import android.content.Intent
import at.renehollander.photosofinterest.BasePresenter
import at.renehollander.photosofinterest.BaseView
import at.renehollander.photosofinterest.auth.domain.error.AuthError

/**
 * Contract for the auth view
 *
 * @author Rene Hollander
 * @version 1.0
 */
interface AuthContract {

    interface View : BaseView {
        fun showProgress()
        fun dismissDialog()
        fun showAuthError(error: AuthError)
        fun close()
    }

    interface Presenter : BasePresenter<View> {
        fun googleSignIn()
        fun facebookSignIn()
        fun result(requestCode: Int, resultCode: Int, data: Intent?)
    }
}
