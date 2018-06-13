package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.PointOfInterest
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.storage.FirebaseStorage
import java.io.FileInputStream
import java.util.*
import javax.inject.Inject

/**
 * Implementation of DataSource using a Repository Pattern.
 * This can be used to have both, a local and a remote Repository
 * and merge their operations in this class
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
@ApplicationScoped
class PostDataRepository @Inject constructor(
        private val db: FirebaseFirestore,
        private val storage: FirebaseStorage,
        private val userManager: UserManager
) : PostDataSource {
    override fun loadPosts(filter: PostDataSource.Filter, callback: LoadRecordCallback<Post>) {
        callback.onRecordsLoaded(mutableListOf())
    }

    override fun createPost(challenge: Challenge, title: String, image: String, origin: GeoPoint, callback: GetRecordCallback<Post>) {
        val data = mutableMapOf<String, Any>()
        val poi = PointOfInterest() // TODO calculate!

        data["user"] = db.collection("users").document(userManager.getCurrentUser()!!.id)
        data["poi"] = db.collection("challenges").document(challenge.id).collection("pois").document(poi.id)
        data["title"] = title
        data["origin"] = origin
        data["upvotes"] = 0
        data["downvotes"] = 0

        uploadPostImage(image, object : GetRecordCallback<String> {
            override fun onRecordLoaded(record: String) {
                data["image"] = record

                db.collection("challenges").document(challenge.id).collection("posts").add(data).addOnSuccessListener {
                    callback.onRecordLoaded(Post(
                            id = it.id,
                            challenge = challenge,
                            poi = poi,
                            upvotes = 0,
                            downvotes = 0,
                            image = image,
                            title = title,
                            user = userManager.getCurrentUser()!!,
                            origin = origin
                    ))
                }.addOnFailureListener {
                    callback.onDataNotAvailable()
                }
            }

            override fun onDataNotAvailable() {
                callback.onDataNotAvailable()
            }
        })
    }

    override fun uploadPostImage(file: String, callback: GetRecordCallback<String>) {
        // Filename must contain extension
        if (!file.contains(".")) {
            callback.onDataNotAvailable()
            return
        }
        val storageRef = this.storage.reference
        val id = UUID.randomUUID()
        val extension = file.substring(file.lastIndexOf(".") + 1..file.length)
        val internal = "posts/$id.$extension"
        val imageRef = storageRef.child(internal)
        val uploadTask = imageRef.putStream(FileInputStream(file))

        uploadTask.addOnSuccessListener {
            callback.onRecordLoaded(internal)
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }
    }
}