package at.renehollander.photosofinterest.inject.modules

import at.renehollander.photosofinterest.data.source.PostDataRepository
import at.renehollander.photosofinterest.data.source.PostDataSource
import at.renehollander.photosofinterest.data.source.ScoreboardDataRepository
import at.renehollander.photosofinterest.data.source.ScoreboardDataSource
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    @ApplicationScoped
    abstract fun bindPostDataSource(repository: PostDataRepository): PostDataSource

    @Binds
    @ApplicationScoped
    abstract fun bindScoreboardDataSource(repository: ScoreboardDataRepository): ScoreboardDataSource
}