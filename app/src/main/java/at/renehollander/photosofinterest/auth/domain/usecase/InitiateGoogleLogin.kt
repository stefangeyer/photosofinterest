package at.renehollander.photosofinterest.auth.domain.usecase

import android.support.v4.app.FragmentActivity
import at.renehollander.photosofinterest.UseCase
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class InitiateGoogleLogin @Inject constructor(
        val auth: FirebaseAuth
) : UseCase<InitiateGoogleLogin.RequestValues, InitiateGoogleLogin.ResponseValue>(), GoogleApiClient.OnConnectionFailedListener {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            // Configure Google Sign In
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).apply {
                requestIdToken(requestValues.webClientId)
                requestEmail()
            }.build()

            // Build a GoogleApiClient with access to the Google Sign-In API and the options specified
            val googleApiClient = GoogleApiClient.Builder(requestValues.activity).apply {
                enableAutoManage(requestValues.activity, this@InitiateGoogleLogin)
                addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            }.build()

            requestValues.activity.startActivityForResult(
                    Auth.GoogleSignInApi.getSignInIntent(googleApiClient),
                    requestValues.activityRequestCode
            )
            useCaseCallback?.onSuccess(ResponseValue())
        } else useCaseCallback?.onError()
    }

    override fun onConnectionFailed(result: ConnectionResult) {
    }

    class RequestValues(val activity: FragmentActivity,
                        val webClientId: String,
                        val activityRequestCode: Int) : UseCase.RequestValues

    class ResponseValue : UseCase.ResponseValue
}