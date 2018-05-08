package at.renehollander.photosofinterest.feed

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FeedModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun feedFragment(): FeedFragment

    @Binds
    @ActivityScoped
    abstract fun bindView(fragment: FeedFragment): FeedContract.View

    @Binds
    @ActivityScoped
    abstract fun bindPresenter(presenter: FeedPresenter): FeedContract.Presenter
}