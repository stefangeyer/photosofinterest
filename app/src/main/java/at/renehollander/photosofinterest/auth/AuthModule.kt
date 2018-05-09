package at.renehollander.photosofinterest.auth

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import dagger.Binds
import dagger.Module

@Module(includes = [
])
abstract class AuthModule {

    @Binds
    @ActivityScoped
    abstract fun authPresenter(presenter: AuthPresenter): AuthContract.Presenter

}
