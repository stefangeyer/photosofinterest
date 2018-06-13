package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.data.source.firebase.FirebaseUserManager
import com.google.firebase.auth.FirebaseAuth

interface UserManager {

    companion object {
        private var instance: UserManager? = null

        fun getInstance(): UserManager {
            if (instance == null) {
                instance = FirebaseUserManager(FirebaseAuth.getInstance())
            }
            return instance!!
        }
    }

    fun getCurrentUser(): User?
    fun isLoggedIn(): Boolean
    fun logout()
}