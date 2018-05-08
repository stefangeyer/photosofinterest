package at.renehollander.photosofinterest.auth

import android.os.Bundle
import at.renehollander.photosofinterest.R
import com.google.android.gms.common.SignInButton
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity(), AuthContract.View {

    @Inject
    lateinit var presenter: AuthContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        btn_google.setSize(SignInButton.SIZE_WIDE)
        btn_google.setOnClickListener {
            presenter.googleSignIn()
        }
    }

    override fun close() {
        finish()
    }

}
