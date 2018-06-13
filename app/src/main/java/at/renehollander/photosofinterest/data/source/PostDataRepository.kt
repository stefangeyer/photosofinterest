package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.PointOfInterest
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
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
    override fun loadPosts(filter: PostDataSource.Filter, callback: LoadRecordCallback<Post>) {
        callback.onRecordsLoaded(mutableListOf())
    }

    override fun addPost(challenge: Challenge, poi: PointOfInterest, title: String, image: String, origin: GeoPoint, callback: GetRecordCallback<Post>) {
        val data = mutableMapOf<String, Any>()
        data["user"] = db.collection("users").document(userManager.getCurrentUser()!!.id)
        data["poi"] = db.collection("challenges").document(challenge.id).collection("pois").document(poi.id)
        data["title"] = title
        data["image"] = image
        data["origin"] = origin
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
                    user = userManager.getCurrentUser()!!,
                    origin = origin
            ))
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }
    }
}