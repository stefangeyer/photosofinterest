package at.renehollander.photosofinterest.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import at.renehollander.photosofinterest.R
import at.renehollander.photosofinterest.auth.domain.error.AuthError
import at.renehollander.photosofinterest.auth.domain.error.AuthError.*
import com.google.android.gms.common.SignInButton
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity(), AuthContract.View {

    @Inject
    lateinit var presenter: AuthContract.Presenter

    private var currentDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        btn_google.setSize(SignInButton.SIZE_WIDE)
        btn_google.setOnClickListener { presenter.googleSignIn() }

        presenter.takeView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.result(requestCode, resultCode, data)
    }

    override fun showProgress() {
        currentDialog = AlertDialog.Builder(this)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.auth_pending))
                .create().apply { show() }
    }

    private fun showMessage(message: String) {
        currentDialog = AlertDialog.Builder(this).setTitle(getString(R.string.app_name))
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, { d, _ -> d.dismiss() }).create().apply { show() }
    }

    override fun dismissDialog() {
        if (currentDialog != null && currentDialog!!.isShowing) {
            currentDialog!!.cancel()
        }
    }

    override fun showAuthError(error: AuthError) {
        val messageId = when (error) {
            EMAIL_ALREADY_IN_USE -> R.string.error_email_already_in_use
            INVALID_EMAIL -> R.string.error_invalid_email
            WEAK_PASSWORD -> R.string.error_password_too_short
            WRONG_PASSWORD -> R.string.error_incorrect_credentials
            USER_DISABLED -> R.string.error_user_not_available
            USER_NOT_FOUND -> R.string.error_user_not_available
            WRONG_PROVIDER -> R.string.error_wrong_provider
            OTHER -> R.string.error_other
            GOOGLE_NO_SUCCESS -> R.string.error_google_not_successful
        }

        showMessage(getString(messageId))
    }

    override fun close() {
        finish()
    }
}
