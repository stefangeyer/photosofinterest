package at.renehollander.photosofinterest.data

import java.time.LocalDateTime

data class Challenge(var title: String, var start: LocalDateTime, var end: LocalDateTime, var description: String, var regions: List<Region>, var pois: List<PointOfInterest>)
