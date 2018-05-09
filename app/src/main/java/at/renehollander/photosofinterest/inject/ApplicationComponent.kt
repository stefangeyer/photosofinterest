package at.renehollander.photosofinterest.inject

import at.renehollander.photosofinterest.PhotosOfInterest
import at.renehollander.photosofinterest.inject.modules.AndroidBindingModule
import at.renehollander.photosofinterest.inject.modules.ApplicationModule
import at.renehollander.photosofinterest.inject.modules.DataModule
import at.renehollander.photosofinterest.inject.modules.UseCaseModule
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@ApplicationScoped
@Component(modules = [(ApplicationModule::class), (AndroidBindingModule::class), (AndroidSupportInjectionModule::class), (UseCaseModule::class), (DataModule::class)])
interface ApplicationComponent : AndroidInjector<PhotosOfInterest> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<PhotosOfInterest>()
}