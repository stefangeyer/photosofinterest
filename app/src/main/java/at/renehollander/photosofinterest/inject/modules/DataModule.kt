package at.renehollander.photosofinterest.inject.modules

import at.renehollander.photosofinterest.data.source.PostDataRepository
import at.renehollander.photosofinterest.data.source.PostDataSource
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    @ApplicationScoped
    abstract fun bindDataSource(repository: PostDataRepository): PostDataSource
}