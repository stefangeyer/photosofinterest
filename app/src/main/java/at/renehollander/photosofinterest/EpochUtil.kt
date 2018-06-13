package at.renehollander.photosofinterest

import com.google.firebase.Timestamp
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset


fun timestampToLocalDateTime(ts: Timestamp): LocalDateTime {
    return LocalDateTime.from(Instant.ofEpochSecond(ts.seconds).atOffset(ZoneOffset.UTC))
}

fun localDateTimeToTimestamp(ldt: LocalDateTime): Timestamp {
    return Timestamp(ldt.toInstant(ZoneOffset.UTC).epochSecond, 0)
}