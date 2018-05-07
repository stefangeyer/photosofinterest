package at.renehollander.photosofinterest.feed.post

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class PostModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun postFragment(): PostFragment

    @Binds
    @ActivityScoped
    abstract fun bindPresenter(presenter: PostPresenter): PostContract.Presenter
}