package at.renehollander.photosofinterest.data

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class Post(var id: String = "",
                var user: User,
                var challenge: Challenge,
                var title: String,
                var image: String,
                var upvotes: Int,
                var downvotes: Int,
                var origin: GeoPoint,
                var timestamp: Timestamp = Timestamp.now(),
                var poi: PointOfInterest) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readParcelable(User::class.java.classLoader),
            parcel.readParcelable(Challenge::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            GeoPoint(parcel.readDouble(), parcel.readDouble()),
            Timestamp(parcel.readLong(), parcel.readInt()),
            parcel.readParcelable(PointOfInterest::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeParcelable(user, flags)
        parcel.writeParcelable(challenge, flags)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeInt(upvotes)
        parcel.writeInt(downvotes)
        parcel.writeLong(timestamp.seconds)
        parcel.writeInt(timestamp.nanoseconds)
        parcel.writeParcelable(poi, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Post> {
        override fun createFromParcel(parcel: Parcel): Post {
            return Post(parcel)
        }

        override fun newArray(size: Int): Array<Post?> {
            return arrayOfNulls(size)
        }
    }
}
