package at.renehollander.photosofinterest.data.source

import android.net.Uri
import android.util.Log
import at.renehollander.photosofinterest.data.*
import at.renehollander.photosofinterest.inject.scopes.ApplicationScoped
import at.renehollander.photosofinterest.timestampToLocalDateTime
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.storage.FirebaseStorage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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
        private val userManager: UserManager,
        val fbFunctions: FirebaseFunctions
) : PostDataSource {

    init {
        EventBus.getDefault().register(this);
    }

    override fun loadPosts(challenge: Challenge, callback: LoadRecordCallback<Post>) {
        db.collection("challenges").document(challenge.id).collection("posts").get().continueWithTask {
            return@continueWithTask Tasks.whenAllSuccess<Post>(it.result.documents.map { postData ->
                val post = Post(
                        id = postData.id,
                        user = User(),
                        challenge = challenge,
                        title = postData["title"] as String,
                        image = postData["image"] as String,
                        upvotes = (postData["upvotes"] as Long).toInt(),
                        downvotes = (postData["downvotes"] as Long).toInt(),
                        origin = postData["origin"] as GeoPoint,
                        timestamp = postData["timestamp"] as Timestamp,
                        poi = PointOfInterest()
                )
                return@map (postData["user"] as DocumentReference).get().continueWith {
                    post.user = User(
                            id = it.result.id,
                            email = it.result["email"] as String,
                            name = it.result["name"] as String,
                            image = it.result["image"] as String
                    )
                    return@continueWith post
                }.continueWithTask inner@{
                    return@inner (postData["poi"] as DocumentReference).get().continueWith {
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
        db.collection("challenges").get().continueWithTask { challengesData ->
            return@continueWithTask Tasks.whenAllSuccess<List<Post>>(challengesData.result.documents.map { challengeData ->
                @Suppress("UNCHECKED_CAST")
                val challenge = Challenge()
                challenge.id = challengeData.id
                challenge.title = challengeData["title"] as String
                challenge.image = challengeData["image"] as String
                challenge.start = timestampToLocalDateTime(challengeData["start"] as Timestamp)
                challenge.end = timestampToLocalDateTime(challengeData["end"] as Timestamp)
                challenge.description = challengeData["description"] as String
                challenge.regions = (challengeData["regions"] as List<Map<String, Any?>>).map mapi@{
                    val region = Region()
                    region.description = it["description"] as String
                    region.region = it["region"] as List<GeoPoint>
                    return@mapi region
                }

                return@map challengeData.reference.collection("posts").get().continueWithTask inner@{
                    return@inner Tasks.whenAllSuccess<Post>(it.result.documents.map postDataMap@{ postData ->
                        val post = Post(
                                id = postData.id,
                                user = User(),
                                challenge = challenge,
                                title = postData["title"] as String,
                                image = postData["image"] as String,
                                upvotes = (postData["upvotes"] as Long).toInt(),
                                downvotes = (postData["downvotes"] as Long).toInt(),
                                origin = postData["origin"] as GeoPoint,
                                timestamp = postData["timestamp"] as Timestamp,
                                poi = PointOfInterest()
                        )
                        return@postDataMap (postData["user"] as DocumentReference).get().continueWith {
                            post.user = User(
                                    id = it.result.id,
                                    email = it.result["email"] as String,
                                    name = it.result["name"] as String,
                                    image = it.result["image"] as String
                            )
                            return@continueWith post
                        }.continueWithTask pointOfInterestMap@{
                            return@pointOfInterestMap (postData["poi"] as DocumentReference).get().continueWith {
                                post.poi = PointOfInterest(
                                        id = it.result.id,
                                        name = it.result["name"] as String? ?: "",
                                        location = it.result["location"] as GeoPoint?
                                                ?: GeoPoint(0.0, 0.0)
                                )
                                return@continueWith post
                            }
                        }
                    })
                }
            })
        }.addOnSuccessListener {
            val items = it.flatMap { it.toList() }
            callback.onRecordsLoaded(when (filter) {
                PostDataSource.Filter.TOP -> items.sortedByDescending {
                    it.upvotes - it.downvotes
                }
                PostDataSource.Filter.RECENT -> items.sortedByDescending {
                    it.timestamp.seconds
                }
                PostDataSource.Filter.RISING -> items.shuffled() // This is our special algorithm to show people only the most important stuff.
            })
        }.addOnFailureListener {
            Log.e(TAG, "Error fetching posts", it)
            callback.onDataNotAvailable()
        }
    }

    override fun loadPosts(user: User, callback: LoadRecordCallback<Post>) {
        db.collection("challenges").get().continueWithTask { challengesData ->
            return@continueWithTask Tasks.whenAllSuccess<List<Post>>(challengesData.result.documents.map { challengeData ->
                @Suppress("UNCHECKED_CAST")
                val challenge = Challenge()
                challenge.id = challengeData.id
                challenge.title = challengeData["title"] as String
                challenge.image = challengeData["image"] as String
                challenge.start = timestampToLocalDateTime(challengeData["start"] as Timestamp)
                challenge.end = timestampToLocalDateTime(challengeData["end"] as Timestamp)
                challenge.description = challengeData["description"] as String
                challenge.regions = (challengeData["regions"] as List<Map<String, Any?>>).map mapi@{
                    val region = Region()
                    region.description = it["description"] as String
                    region.region = it["region"] as List<GeoPoint>
                    return@mapi region
                }

                return@map challengeData.reference.collection("posts").get().continueWithTask inner@{
                    return@inner Tasks.whenAllSuccess<Post>(it.result.documents.filter { (it["user"] as DocumentReference).id.equals(user.id) }.map postDataMap@{ postData ->
                        val post = Post(
                                id = postData.id,
                                user = User(),
                                challenge = challenge,
                                title = postData["title"] as String,
                                image = postData["image"] as String,
                                upvotes = (postData["upvotes"] as Long).toInt(),
                                downvotes = (postData["downvotes"] as Long).toInt(),
                                origin = postData["origin"] as GeoPoint,
                                timestamp = postData["timestamp"] as Timestamp,
                                poi = PointOfInterest()
                        )
                        return@postDataMap (postData["user"] as DocumentReference).get().continueWith {
                            post.user = User(
                                    id = it.result.id,
                                    email = it.result["email"] as String,
                                    name = it.result["name"] as String,
                                    image = it.result["image"] as String
                            )
                            return@continueWith post
                        }.continueWithTask pointOfInterestMap@{
                            return@pointOfInterestMap (postData["poi"] as DocumentReference).get().continueWith {
                                post.poi = PointOfInterest(
                                        id = it.result.id,
                                        name = it.result["name"] as String? ?: "",
                                        location = it.result["location"] as GeoPoint?
                                                ?: GeoPoint(0.0, 0.0)
                                )
                                return@continueWith post
                            }
                        }
                    })
                }
            })
        }.addOnSuccessListener {
            callback.onRecordsLoaded(it.flatMap { it.toList() })
        }.addOnFailureListener {
            Log.e(TAG, "Error fetching posts", it)
            callback.onDataNotAvailable()
        }
    }

    private fun executeVote(type: String, post: Post, callback: GetRecordCallback<Post>) {
        fbFunctions.getHttpsCallable(type)
                .call(mapOf(Pair("challenge", post.challenge.id), Pair("post", post.id)))
                .addOnSuccessListener {
                    val postData = it.data as Map<String, Any>
                    callback.onRecordLoaded(Post(upvotes = postData["upvotes"] as Int, downvotes = postData["downvotes"] as Int))
                }.addOnFailureListener {
                    Log.e(ScoreboardDataRepository.TAG, "Error upvoting post", it)
                    callback.onDataNotAvailable()
                }
    }

    override fun upvotePost(post: Post, callback: GetRecordCallback<Post>) {
        executeVote("upvote", post, callback)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUpvoteEvent(event: UpvoteEvent) {
        upvotePost(event.post, event.callback)
    }

    override fun downvotePost(post: Post, callback: GetRecordCallback<Post>) {
        executeVote("downvote", post, callback)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDownvoteEvent(event: DownvoteEvent) {
        downvotePost(event.post, event.callback)
    }

    override fun createPost(challenge: Challenge, title: String, image: String, origin: GeoPoint, callback: GetRecordCallback<Post>) {
        var nearest: PointOfInterest? = null
        var nearestDist: Double = Double.MAX_VALUE
        for (poi in challenge.pois) {
            val dist = haversine(poi.location, origin)
            if (dist < nearestDist) {
                nearestDist = dist
                nearest = poi
            }
        }
        // TODO: check radius!!!!

        if (nearest == null) callback.onDataNotAvailable()
        nearest!!


        val data = mutableMapOf<String, Any>()
        data["user"] = db.collection("users").document(userManager.getCurrentUser()!!.id)
        data["poi"] = db.collection("challenges").document(challenge.id).collection("pois").document(nearest.id)
        data["title"] = title
        data["origin"] = origin
        data["upvotes"] = 0
        data["downvotes"] = 0
        data["timestamp"] = Timestamp.now()

        uploadPostImage(image, object : GetRecordCallback<String> {
            override fun onRecordLoaded(record: String) {
                data["image"] = record

                db.collection("challenges").document(challenge.id).collection("posts").add(data).addOnSuccessListener {
                    callback.onRecordLoaded(Post(
                            id = it.id,
                            challenge = challenge,
                            poi = nearest,
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
        val storageRef = this.storage.reference
        val id = UUID.randomUUID().toString().replace("-", "")
        val internal = "posts/$id.jpg"
        val imageRef = storageRef.child(internal)
        val uploadTask = imageRef.putFile(Uri.parse(file))

        uploadTask.addOnSuccessListener {
            callback.onRecordLoaded(internal)
        }.addOnFailureListener {
            callback.onDataNotAvailable()
        }
    }

    private fun haversine(c1: GeoPoint, c2: GeoPoint): Double {
        return haversine(c1.latitude, c2.latitude, c1.longitude, c2.longitude)
    }

    private fun haversine(lat1: Double, lat2: Double, lon1: Double, lon2: Double): Double {
        val R = 6371e3 // metres
        val phi1 = Math.toRadians(lat1)
        val phi2 = Math.toRadians(lat2)
        val deltaphi = Math.toRadians(lat2 - lat1)
        val deltalambda = Math.toRadians(lon2 - lon1)

        val a = Math.sin(deltaphi / 2) * Math.sin(deltaphi / 2) + Math.cos(phi1) * Math.cos(phi2) *
                Math.sin(deltalambda / 2) * Math.sin(deltalambda / 2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return R * c
    }

    companion object {
        const val TAG = "PostDataRepository"
    }
}