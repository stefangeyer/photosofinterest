package at.renehollander.photosofinterest

import at.renehollander.photosofinterest.inject.DaggerApplicationComponent
import com.facebook.drawee.backends.pipeline.Fresco
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class PhotosOfInterest: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? =
        DaggerApplicationComponent.builder().create(this)
}