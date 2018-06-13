package at.renehollander.photosofinterest.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.GeoPoint

data class PointOfInterest(var id: String = "", var name: String = "", var location: GeoPoint = GeoPoint(0.0, 0.0), var radius: Double = 0.0) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            GeoPoint(parcel.readDouble(), parcel.readDouble()),
            parcel.readDouble())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeDouble(location.latitude)
        parcel.writeDouble(location.longitude)
        parcel.writeDouble(radius)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<PointOfInterest> {
        override fun createFromParcel(parcel: Parcel): PointOfInterest = PointOfInterest(parcel)

        override fun newArray(size: Int): Array<PointOfInterest?> = arrayOfNulls(size)
    }
}

data class Region(var description: String = "", var region: List<GeoPoint> = mutableListOf()) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(), readRegion(parcel))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeList(region)
    }

    override fun describeContents(): Int = 0

    companion object {
        val CREATOR = object : Parcelable.Creator<Region> {
            override fun createFromParcel(parcel: Parcel): Region = Region(parcel)
            override fun newArray(size: Int): Array<Region?> = arrayOfNulls(size)
        }

        private fun readRegion(parcel: Parcel): List<GeoPoint> {
            val list = arrayListOf<GeoPoint>()
            parcel.readList(list, GeoPoint::class.java.classLoader)
            return list
        }
    }
}
