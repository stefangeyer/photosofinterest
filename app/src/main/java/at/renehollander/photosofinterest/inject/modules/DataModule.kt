package at.renehollander.photosofinterest.inject.modules

import at.renehollander.photosofinterest.data.source.EntityDataRepository
import at.renehollander.photosofinterest.data.source.EntityDataSource
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    @ApplicationScoped
    abstract fun bindDataSource(repository: EntityDataRepository): EntityDataSource
}