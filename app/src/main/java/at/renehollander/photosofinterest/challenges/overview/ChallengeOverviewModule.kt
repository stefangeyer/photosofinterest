package at.renehollander.photosofinterest.challenges.overview

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChallengeOverviewModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun challengeOverviewFragment(): ChallengeOverviewFragment
}