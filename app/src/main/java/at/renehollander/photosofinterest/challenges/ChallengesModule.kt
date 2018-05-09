package at.renehollander.photosofinterest.challenges

import at.renehollander.photosofinterest.challenge.ChallengeModule
import at.renehollander.photosofinterest.challenges.overview.ChallengeOverviewModule
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ChallengeOverviewModule::class, ChallengeModule::class])
abstract class ChallengesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun challengesFragment(): ChallengesFragment

    @Binds
    @ActivityScoped
    abstract fun bindPresenter(presenter: ChallengesPresenter): ChallengesContract.Presenter
}