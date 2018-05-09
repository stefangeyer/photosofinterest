package at.renehollander.photosofinterest.challenge.details

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChallengeDetailsModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun challengeDetailsFragment(): ChallengeDetailsFragment

    @Binds
    @ActivityScoped
    abstract fun bindView(fragment: ChallengeDetailsFragment): ChallengeDetailsContract.View

    @Binds
    @ActivityScoped
    abstract fun bindPresenter(presenter: ChallengeDetailsPresenter): ChallengeDetailsContract.Presenter
}