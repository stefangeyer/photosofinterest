package at.renehollander.photosofinterest.inject.modules

import android.app.Application
import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.image.ImageActivity
import at.renehollander.photosofinterest.image.ImageModule
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import at.renehollander.photosofinterest.main.MainActivity
import at.renehollander.photosofinterest.main.MainModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModule {

    // PhotosOfInterest -> Application

    @Binds
    @ApplicationScoped
    abstract fun bindApplication(photosOfInterest: PhotosOfInterest): Application

    // activities

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ImageModule::class])
    abstract fun imageActivity(): ImageActivity

}