package at.renehollander.photosofinterest.inject.modules

import android.app.Application
import android.content.Context
import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.auth.AuthActivity
import at.renehollander.photosofinterest.auth.AuthModule
import at.renehollander.photosofinterest.image.ImageActivity
import at.renehollander.photosofinterest.image.ImageModule
import at.renehollander.photosofinterest.inject.annotation.ApplicationContext
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import at.renehollander.photosofinterest.main.MainActivity
import at.renehollander.photosofinterest.main.MainModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBindingModule {

    // PhotosOfInterest -> Application -> Context

    @Binds
    @ApplicationScoped
    abstract fun bindApplication(photosOfInterest: PhotosOfInterest): Application

    @Binds
    @ApplicationScoped
    @ApplicationContext
    abstract fun bindApplicationContext(application: Application): Context

    // activities

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [ImageModule::class])
    abstract fun imageActivity(): ImageActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun authActivity(): AuthActivity

}