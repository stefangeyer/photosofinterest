package at.renehollander.photosofinterest.data.source

import at.renehollander.photosofinterest.data.Challenge
import at.renehollander.photosofinterest.data.PointOfInterest
import at.renehollander.photosofinterest.data.Post
import at.renehollander.photosofinterest.data.User
import com.google.firebase.firestore.GeoPoint

/**
 * Example data source that provides operations for a sample entity
 *
 * @author Stefan Geyer, Rene Hollander
 * @version 1.0
 */
interface PostDataSource {
    fun loadPosts(filter: Filter, callback: LoadRecordCallback<Post>)

    enum class Filter {
        RISING, RECENT, TOP
    }

    fun createPost(challenge: Challenge, title: String, image: String, origin: GeoPoint, callback: GetRecordCallback<Post>)
    fun uploadPostImage(file: String, callback: GetRecordCallback<String>)
    fun addPost(challenge: Challenge, poi: PointOfInterest, title: String, image: String, origin: GeoPoint, callback: GetRecordCallback<Post>)
    fun loadPosts(challenge: Challenge, callback: LoadRecordCallback<Post>)
    fun loadPosts(user: User, callback: LoadRecordCallback<Post>)
}