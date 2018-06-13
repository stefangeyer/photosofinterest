package at.renehollander.photosofinterest.data

import org.threeten.bp.LocalDateTime

data class Challenge(var id: String = "",
                     var title: String = "",
                     var image: String = "",
                     var start: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0),
                     var end: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0),
                     var description: String = "",
                     var regions: List<Region> = mutableListOf(),
                     var pois: List<PointOfInterest> = mutableListOf())
