package at.renehollander.photosofinterest.scoreboard

import at.renehollander.photosofinterest.inject.scopes.ActivityScoped
import at.renehollander.photosofinterest.inject.scopes.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
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