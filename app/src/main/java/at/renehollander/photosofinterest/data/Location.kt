package at.renehollander.photosofinterest.data

data class Point(var lat: Double = 0.0, var long: Double = 0.0)

data class PointOfInterest(var name: String = "", var location: Point = Point(), var radius: Int = 0)

data class Region(var description: String = "", var region: List<Point> = mutableListOf())
