package at.renehollander.photosofinterest.data

import com.google.firebase.firestore.GeoPoint

data class Post(var id: String = "", var user: User, var challenge: Challenge, var title: String, var image: Image,
                var upvotes: Int, var downvotes: Int, var origin: GeoPoint, var poi: PointOfInterest)