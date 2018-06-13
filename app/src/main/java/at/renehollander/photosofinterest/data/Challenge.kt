package at.renehollander.photosofinterest.data

import android.os.Parcel
import android.os.Parcelable
import org.threeten.bp.LocalDateTime

data class Challenge(var id: String = "",
                     var title: String = "",
                     var image: String = "",
                     var start: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0),
                     var end: LocalDateTime = LocalDateTime.of(1970, 1, 1, 0, 0, 0),
                     var description: String = "",
                     var regions: List<Region> = mutableListOf(),
                     var pois: List<PointOfInterest> = mutableListOf()) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readSerializable() as LocalDateTime,
            parcel.readSerializable() as LocalDateTime,
            parcel.readString(),
            parcel.createTypedArrayList(Region.CREATOR),
            parcel.createTypedArrayList(PointOfInterest))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeSerializable(start)
        parcel.writeSerializable(end)
        parcel.writeString(description)
        parcel.writeTypedList(regions)
        parcel.writeTypedList(pois)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Challenge> {
        override fun createFromParcel(parcel: Parcel): Challenge {
            return Challenge(parcel)
        }

        override fun newArray(size: Int): Array<Challenge?> {
            return arrayOfNulls(size)
        }
    }
}
