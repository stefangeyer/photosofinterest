package at.renehollander.photosofinterest.scoreboard.ownentry

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScoreboardOwnEntryModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun scoreboardOwnEntryFragment(): ScoreboardOwnEntryFragment

    @Binds
    @ActivityScoped
    abstract fun bindView(fragment: ScoreboardOwnEntryFragment): ScoreboardOwnEntryContract.View

    @Binds
    @ActivityScoped
    abstract fun bindPresenter(presenter: ScoreboardOwnEntryPresenter): ScoreboardOwnEntryContract.Presenter
}