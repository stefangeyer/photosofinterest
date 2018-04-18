package at.renehollander.photosofinterest.inject.modules

import at.renehollander.photosofinterest.UseCaseHandler
import at.renehollander.photosofinterest.UseCaseThreadPoolScheduler
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import dagger.Module
import dagger.Provides

@Module
object UseCaseModule {

    @JvmStatic
    @Provides
    @ApplicationScoped
    fun bindUseCaseHandler(): UseCaseHandler = UseCaseHandler(UseCaseThreadPoolScheduler())
}