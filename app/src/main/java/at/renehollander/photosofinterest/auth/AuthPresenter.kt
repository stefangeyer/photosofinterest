package at.renehollander.photosofinterest.auth

import android.content.Intent
import android.support.v4.app.FragmentActivity
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.auth.domain.error.AuthError
import at.renehollander.photosofinterest.auth.domain.usecase.FinishGoogleLogin
import at.renehollander.photosofinterest.auth.domain.usecase.InitiateGoogleLogin
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import javax.inject.Inject

class AuthPresenter @Inject constructor(
        private val useCaseHandler: UseCaseHandler,
        private val initiateGoogleLogin: InitiateGoogleLogin,
        private val finishGoogleLogin: FinishGoogleLogin
) : AuthContract.Presenter {

    companion object {
        const val GOOGLE_SIGN_IN = 0
    }

    private var view: AuthContract.View? = null

    override fun takeView(view: AuthContract.View) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun result(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            GOOGLE_SIGN_IN -> {
                // Google
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                finishGoogleSignIn(result)
            }
        }
    }

    override fun googleSignIn() {
        val activity = getActivity()
        if (activity != null) {
            view?.showProgress()
            useCaseHandler.execute(initiateGoogleLogin,
                    InitiateGoogleLogin.RequestValues(
                            activity,
                            activity.getString(R.string.default_web_client_id),
                            GOOGLE_SIGN_IN
                    ), {}, {})
        }
    }

    private fun finishGoogleSignIn(signInResult: GoogleSignInResult) {
        useCaseHandler.execute(finishGoogleLogin, FinishGoogleLogin.RequestValues(signInResult), {
            view?.dismissDialog()
            if (it.success) view?.close()
            else view?.showAuthError(it.error!!)
        }, {
            view?.dismissDialog()
            view?.showAuthError(AuthError.OTHER)
        })
    }

    override fun facebookSignIn() {
        view?.close() // TODO: not implemented
    }

    private fun getActivity() = view as FragmentActivity?

}
