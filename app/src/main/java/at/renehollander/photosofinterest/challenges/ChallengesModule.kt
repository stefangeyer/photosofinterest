package at.renehollander.photosofinterest.challenges

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChallengesModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun challengesFragment(): ChallengesFragment

    @Binds
    @ActivityScoped
    abstract fun bindView(fragment: ChallengesFragment): ChallengesContract.View

    @Binds
    @ActivityScoped
    abstract fun bindPresenter(presenter: ChallengesPresenter): ChallengesContract.Presenter
}