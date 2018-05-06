package at.renehollander.photosofinterest.image

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import dagger.Binds
import dagger.Module

@Module
abstract class ImageModule {

    @Binds
    @ActivityScoped
    abstract fun imagePresenter(presenter: ImagePresenter): ImageContract.Presenter
}