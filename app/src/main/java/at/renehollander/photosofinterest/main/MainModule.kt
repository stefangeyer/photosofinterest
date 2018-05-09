package at.renehollander.photosofinterest.main

import android.content.Context
import android.support.v4.app.FragmentManager
import at.renehollander.photosofinterest.challenge.ChallengeModule
import at.renehollander.photosofinterest.challenges.ChallengesModule
import at.renehollander.photosofinterest.feed.FeedModule
import at.renehollander.photosofinterest.inject.annotation.ApplicationContext
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.profile.ProfileModule
import at.renehollander.photosofinterest.scoreboard.ScoreboardModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector


@Module(includes = [
    MainModule.Declarations::class,
    ChallengeModule::class,
    ChallengesModule::class,
    FeedModule::class,
    ProfileModule::class,
    ScoreboardModule::class
])
abstract class MainModule {

    @Module
    interface Declarations {
        @ContributesAndroidInjector
        fun mainActivity(): MainActivity

        @Binds
        @ActivityScoped
        fun mainPresenter(presenter: MainPresenter): MainContract.Presenter
    }

    @Provides
    @ActivityScoped
    fun fragmentManager(activity: MainActivity): FragmentManager = activity.supportFragmentManager

}