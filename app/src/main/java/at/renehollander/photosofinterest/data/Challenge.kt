package at.renehollander.photosofinterest.data

import org.threeten.bp.LocalDateTime

data class Challenge(var title: String, var image: Image, var start: LocalDateTime,
                     var end: LocalDateTime, var description: String,
                     var regions: List<Region>, var pois: List<PointOfInterest>)
