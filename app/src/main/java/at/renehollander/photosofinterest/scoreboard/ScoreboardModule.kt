package at.renehollander.photosofinterest.scoreboard

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import at.renehollander.photosofinterest.scoreboard.ownentry.ScoreboardOwnEntryModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [
    ScoreboardOwnEntryModule::class
])
abstract class ScoreboardModule {

    @FragmentScoped
    @ContributesAndroidInjector
    abstract fun scoreboardFragment(): ScoreboardFragment

    @Binds
    @ActivityScoped
    abstract fun bindView(fragment: ScoreboardFragment): ScoreboardContract.View

    @Binds
    @ActivityScoped
    abstract fun bindPresenter(presenter: ScoreboardPresenter): ScoreboardContract.Presenter
}