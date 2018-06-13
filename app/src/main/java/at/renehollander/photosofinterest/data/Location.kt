package at.renehollander.photosofinterest.data

import com.google.firebase.firestore.GeoPoint

data class PointOfInterest(var id: String = "", var name: String = "", var location: GeoPoint = GeoPoint(0.0, 0.0), var radius: Double = 0.0)

data class Region(var description: String = "", var region: List<GeoPoint> = mutableListOf())
