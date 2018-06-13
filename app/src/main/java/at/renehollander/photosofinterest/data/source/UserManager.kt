package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.User

interface UserManager {
    fun getCurrentUser(): User?
    fun logout()
}