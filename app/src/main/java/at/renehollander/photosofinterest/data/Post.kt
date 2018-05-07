package at.renehollander.photosofinterest.data

data class Post(var user: User, var challenge: Challenge, var title: String, var image: Image,
                var upvotes: Int, var downvotes: Int, var origin: Point, var poi: PointOfInterest)
