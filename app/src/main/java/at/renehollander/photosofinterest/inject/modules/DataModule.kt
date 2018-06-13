package at.renehollander.photosofinterest.inject.modules

import at.renehollander.photosofinterest.data.source.*
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

    @Binds
    @ApplicationScoped
    abstract fun bindChallengeDataSource(repository: ChallengeDataRepository): ChallengeDataSource

    @Binds
    @ApplicationScoped
    abstract fun bindUserDataSource(repository: UserDataRepository): UserDataSource
}