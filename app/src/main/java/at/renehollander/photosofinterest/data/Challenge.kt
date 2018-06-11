package at.renehollander.photosofinterest.data

import at.renehollander.photosofinterest.localDateTimeToTimestamp
import at.renehollander.photosofinterest.timestampToLocalDateTime
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import org.threeten.bp.LocalDateTime

data class Challenge(var id: String = "",
                     var title: String = "",
                     var image: Image = Image(),
                     @Exclude var start: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0),
                     @Exclude var end: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0),
                     var description: String = "",
                     var regions: List<Region> = mutableListOf(),
                     var pois: List<PointOfInterest> = mutableListOf()) {

    fun getStartTime(): Timestamp = localDateTimeToTimestamp(start)

    fun setStartTime(ts: Timestamp) {
        start = timestampToLocalDateTime(ts)
    }

    fun getEndTime(): Timestamp = localDateTimeToTimestamp(end)

    fun setEndTime(ts: Timestamp) {
        end = timestampToLocalDateTime(ts)
    }

}
