package at.renehollander.photosofinterest.inject.modules

import at.renehollander.photosofinterest.data.source.UserManager
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
object FirebaseModule {
    @JvmStatic
    @Provides
    @ApplicationScoped
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @JvmStatic
    @Provides
    @ApplicationScoped
    fun provideUserManager(): UserManager = UserManager.getInstance()

    @JvmStatic
    @Provides
    @ApplicationScoped
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

}