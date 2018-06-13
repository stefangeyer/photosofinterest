package at.renehollander.photosofinterest.data.source.firebase

import android.util.Log
import at.renehollander.photosofinterest.auth.SignInEvent
import at.renehollander.photosofinterest.auth.SignOutEvent
import at.renehollander.photosofinterest.data.Image
import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.data.source.GetRecordCallback
import at.renehollander.photosofinterest.data.source.UserDataRepository
import at.renehollander.photosofinterest.data.source.UserManager
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import com.google.firebase.auth.FirebaseAuth
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


@ApplicationScoped
class FirebaseUserManager @Inject constructor(
        private val auth: FirebaseAuth,
        private val userDataRepository: UserDataRepository
) : UserManager {

    private var user: User? = null

    private val listener = FirebaseAuth.AuthStateListener {
        val fbUser = it.currentUser
        if (fbUser != null) {
            // User is signed in
            val email = fbUser.email ?: ""
            val name = fbUser.displayName ?: ""
            val user = User(id = fbUser.uid, email = email, name = name, image = fbUser.photoUrl.toString())

            val metadata = fbUser.metadata!!
            if (metadata.creationTimestamp == metadata.lastSignInTimestamp) {
                // New user!
                userDataRepository.addUser(user, object : GetRecordCallback<User> {
                    override fun onRecordLoaded(record: User) {
                        this@FirebaseUserManager.user = user
                        EventBus.getDefault().post(SignInEvent(user))
                    }

                    override fun onDataNotAvailable() {
                        Log.d(TAG, "Error adding user")
                    }
                })
            } else {
                this.user = user
                EventBus.getDefault().post(SignInEvent(user))
            }
        } else {
            // User is signed out
            this.user = null
            EventBus.getDefault().post(SignOutEvent())
        }
    }

    init {
        this.auth.addAuthStateListener(this.listener)
    }

    override fun getCurrentUser(): User? = this.user

    override fun isLoggedIn(): Boolean = this.user != null

    override fun logout() = this.auth.signOut()

    companion object {
        const val TAG = "FirebaseUserManager"
    }
}