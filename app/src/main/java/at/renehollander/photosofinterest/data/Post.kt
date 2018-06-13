package at.renehollander.photosofinterest.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Post(var id: String = "",
                var user: User = User(),
                var challenge: Challenge = Challenge(),
                var title: String = "",
                var image: String = "",
                var upvotes: Int = 0,
                var downvotes: Int = 0,
                var origin: GeoPoint = GeoPoint(0.0,0.0),
                var timestamp: Timestamp = Timestamp.now(),
                var poi: PointOfInterest = PointOfInterest())
