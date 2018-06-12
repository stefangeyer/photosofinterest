package at.renehollander.photosofinterest.auth.domain.usecase

import at.renehollander.photosofinterest.UseCase
import at.renehollander.photosofinterest.auth.domain.error.AuthError
import at.renehollander.photosofinterest.auth.domain.error.AuthError.*
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class FinishGoogleLogin @Inject constructor(
        val auth: FirebaseAuth
) : UseCase<FinishGoogleLogin.RequestValues, FinishGoogleLogin.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            if (requestValues.result.isSuccess) {
                val credential = GoogleAuthProvider.getCredential(requestValues.result.signInAccount?.idToken, null)
                auth.signInWithCredential(credential).addOnSuccessListener {
                    useCaseCallback?.onSuccess(ResponseValue(true, null))
                }.addOnFailureListener {
                    if (it is FirebaseAuthException) {
                        useCaseCallback?.onSuccess(ResponseValue(false, parseAuthError(it)))
                    } else useCaseCallback?.onError()
                }
            } else useCaseCallback?.onError()
        } else useCaseCallback?.onError()
    }

    private fun parseAuthError(e: FirebaseAuthException): AuthError {
        var authError: AuthError = OTHER
        when (e.errorCode) {
            "ERROR_EMAIL_ALREADY_IN_USE" -> authError = EMAIL_ALREADY_IN_USE
            "ERROR_INVALID_EMAIL" -> authError = INVALID_EMAIL
            "ERROR_WEAK_PASSWORD" -> authError = WEAK_PASSWORD
            "ERROR_WRONG_PASSWORD" -> authError = WRONG_PASSWORD
            "ERROR_USER_DISABLED" -> authError = USER_DISABLED
            "ERROR_USER_NOT_FOUND" -> authError = USER_NOT_FOUND
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> authError = WRONG_PROVIDER
        }
        return authError
    }

    class RequestValues(val result: GoogleSignInResult) : UseCase.RequestValues

    class ResponseValue(val success: Boolean, val error: AuthError?) : UseCase.ResponseValue
}