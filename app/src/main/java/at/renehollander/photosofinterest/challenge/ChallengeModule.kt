package at.renehollander.photosofinterest.challenge

import at.renehollander.photosofinterest.challenge.details.ChallengeDetailsModule
import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    ChallengeDetailsModule::class
])
abstract class ChallengeModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun challengeFragment(): ChallengeFragment

    @Binds
    @ActivityScoped
    abstract fun bindView(fragment: ChallengeFragment): ChallengeContract.View

    @Binds
    @ActivityScoped
    abstract fun bindPresenter(presenter: ChallengePresenter): ChallengeContract.Presenter
}