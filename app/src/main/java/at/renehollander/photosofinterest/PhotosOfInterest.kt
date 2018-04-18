package at.renehollander.photosofinterest

import at.renehollander.photosofinterest.inject.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class PhotosOfInterest: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? =
        DaggerApplicationComponent.builder().create(this)
}