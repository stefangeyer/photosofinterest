package at.renehollander.photosofinterest

import at.renehollander.photosofinterest.inject.DaggerApplicationComponent
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication


class PhotosOfInterest : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        AndroidThreeTen.init(this)
        val firestore = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build()
        firestore.firestoreSettings = settings
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>? =
            DaggerApplicationComponent.builder().create(this)
}