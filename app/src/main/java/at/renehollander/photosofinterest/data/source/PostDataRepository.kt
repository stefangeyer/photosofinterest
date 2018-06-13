package at.renehollander.photosofinterest.data.source

import android.util.Log
import at.renehollander.photosofinterest.data.*
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
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
        val db: FirebaseFirestore,
        val userManager: UserManager
) : PostDataSource {

    override fun loadPosts(challenge: Challenge, callback: LoadRecordCallback<Post>) {
        db.collection("challenges").document(challenge.id).collection("posts").get().continueWithTask {
            return@continueWithTask Tasks.whenAllSuccess<Post>(it.result.documents.map {
                val post = Post(
                        id = it.id,
                        user = User(id = (it["user"] as DocumentReference).id),
                        challenge = challenge,
                        title = it["title"] as String,
                        image = it["image"] as String,
                        upvotes = (it["upvotes"] as Long).toInt(),
                        downvotes = (it["downvotes"] as Long).toInt(),
                        origin = it["origin"] as GeoPoint,
                        timestamp = it["timestamp"] as Timestamp,
                        poi = PointOfInterest(id = (it["poi"] as DocumentReference).id)
                )
                return@map db.collection("users").document(post.user.id).get().continueWith {
                    post.user = User(
                            id = it.result.id,
                            email = it.result["email"] as String,
                            name = it.result["name"] as String,
                            image = Image(uri = it.result["image"] as String)
                    )
                    return@continueWith post
                }.continueWithTask inner@{
                    return@inner db.collection("challenge").document(post.challenge.id).collection("pois").document(post.poi.id).get().continueWith {
                        post.poi = PointOfInterest(
                                id = it.result.id,
                                name = it.result["name"] as String? ?: "",
                                location = it.result["location"] as GeoPoint? ?: GeoPoint(0.0, 0.0)
                        )
                        return@continueWith post
                    }
                }
            })
        }.addOnSuccessListener {
            callback.onRecordsLoaded(it)
        }.addOnFailureListener {
            Log.e(TAG, "Error fetching posts", it)
            callback.onDataNotAvailable()
        }
    }

    override fun loadPosts(filter: PostDataSource.Filter, callback: LoadRecordCallback<Post>) {
        callback.onRecordsLoaded(mutableListOf())
    }

    override fun addPost(challenge: Challenge, poi: PointOfInterest, title: String, image: String, origin: GeoPoint, callback: GetRecordCallback<Post>) {
        val timestamp = Timestamp.now()
        val data = mutableMapOf<String, Any>()
        data["user"] = db.collection("users").document(userManager.getCurrentUser()!!.id)
        data["poi"] = db.collection("challenges").document(challenge.id).collection("pois").document(poi.id)
        data["title"] = title
        data["image"] = image
        data["origin"] = origin
        data["timestamp"] = timestamp
        data["upvotes"] = 0
        data["downvotes"] = 0
        db.collection("challenges").document(challenge.id).collection("posts").add(data).addOnSuccessListener {
            callback.onRecordLoaded(Post(
                    id = it.id,
                    challenge = challenge,
                    poi = poi,
                    upvotes = 0,
                    downvotes = 0,
                    image = image,
                    title = title,
                    timestamp = timestamp,
                    user = userManager.getCurrentUser()!!,
                    origin = origin
            ))
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }
    }

    companion object {
        const val TAG = "PostDataRepository"
    }
}