package at.renehollander.photosofinterest.auth.domain.usecase

import at.renehollander.photosofinterest.UseCase
import at.renehollander.photosofinterest.data.Image
import at.renehollander.photosofinterest.data.User
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class ChangeAuthStateListener @Inject constructor(
        private val auth: FirebaseAuth
) : UseCase<ChangeAuthStateListener.RequestValues, ChangeAuthStateListener.ResponseValue>() {

    private var loggedIn: (User) -> Unit = { _ ->
    }

    private var loggedOut: () -> Unit = {
    }

    private val authStateListener = FirebaseAuth.AuthStateListener {
        val fbUser = it.currentUser
        if (fbUser != null) {
            // User is signed in
            val email = fbUser.email ?: ""
            val name = fbUser.displayName ?: ""
            val user = User(email = email, name = name, image = fbUser.photoUrl.toString())
            loggedIn(user)
        } else {
            // User is signed out
            loggedOut()
        }
    }

    override fun executeUseCase(requestValues: RequestValues?) {
        if (requestValues != null) {
            when (requestValues.action) {
                Action.ADD -> {
                    if (requestValues.loggedIn != null && requestValues.loggedOut != null) {
                        loggedIn = requestValues.loggedIn
                        loggedOut = requestValues.loggedOut
                        auth.addAuthStateListener(authStateListener)
                        useCaseCallback?.onSuccess(ResponseValue())
                    } else useCaseCallback?.onError()
                }
                Action.REMOVE -> auth.removeAuthStateListener(authStateListener)
            }
        } else useCaseCallback?.onError()
    }

    class RequestValues(val action: Action,
                        val loggedIn: ((User) -> Unit)?,
                        val loggedOut: (() -> Unit)?) : UseCase.RequestValues

    class ResponseValue : UseCase.ResponseValue

    enum class Action {
        ADD, REMOVE
    }
}