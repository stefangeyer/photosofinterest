package at.renehollander.photosofinterest.profile

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun profileFragment(): ProfileFragment

    @Binds
    @ActivityScoped
    abstract fun bindView(fragment: ProfileFragment): ProfileContract.View

    @Binds
    @ActivityScoped
    abstract fun bindPresenter(presenter: ProfilePresenter): ProfileContract.Presenter
}