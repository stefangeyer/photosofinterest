package at.renehollander.photosofinterest

import at.renehollander.photosofinterest.data.Image
import at.renehollander.photosofinterest.data.User
import at.renehollander.photosofinterest.inject.DaggerApplicationComponent
import com.facebook.drawee.backends.pipeline.Fresco
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class PhotosOfInterest : DaggerApplication() {

    private var user: User? = null

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        AndroidThreeTen.init(this)
    }

    fun login() {
        user = User(
                "user@example.com",
                "Max Mustermann",
                Image("https://pbs.twimg.com/profile_images/547371527855828992/qSbjhsoI_400x400.jpeg")
        )
    }

    fun isLoggedIn(): Boolean {
        return user != null
    }

    fun logout() {
        user = null
    }

    fun getUser(): User {
        return user!!
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? =
            DaggerApplicationComponent.builder().create(this)
}