package at.renehollander.photosofinterest.main

import at.renehollander.photosofinterest.challenges.ChallengesModule
import at.renehollander.photosofinterest.feed.FeedModule
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.profile.ProfileModule
import at.renehollander.photosofinterest.scoreboard.ScoreboardModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    ChallengesModule::class,
    FeedModule::class,
    ProfileModule::class,
    ScoreboardModule::class
])
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @Binds
    @ActivityScoped
    abstract fun mainPresenter(presenter: MainPresenter): MainContract.Presenter
}