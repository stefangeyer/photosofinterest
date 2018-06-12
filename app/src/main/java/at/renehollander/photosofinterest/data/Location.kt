package at.renehollander.photosofinterest.data

data class Point(var latitude: Double, var longitude: Double)

data class PointOfInterest(var name: String, var location: Point, var radius: Int)

data class Region(var description: String, var region: List<Point>)
