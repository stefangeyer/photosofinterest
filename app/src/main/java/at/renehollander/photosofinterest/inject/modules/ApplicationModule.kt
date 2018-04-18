package at.renehollander.photosofinterest.inject.modules

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import dagger.Module
import dagger.Provides

@Module
object ApplicationModule {

    @JvmStatic
    @Provides
    @ApplicationScoped
    fun provideConnectivityManager(application: Application): ConnectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

//    @JvmStatic
//    @Provides
//    @ApplicationScoped
//    fun provideApplicationDatabase(application: Application): ApplicationDatabase =
//            ApplicationDatabase.createPersistentDatabase(application)

}