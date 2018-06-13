package at.renehollander.photosofinterest.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Post(var id: String = "",
                var user: User,
                var challenge: Challenge,
                var title: String,
                var image: String,
                var upvotes: Int,
                var downvotes: Int,
                var origin: GeoPoint,
                var timestamp: Timestamp = Timestamp.now(),
                var poi: PointOfInterest)
