package at.renehollander.photosofinterest.data

import android.os.Parcel
import android.os.Parcelable

data class User(var id: String = "", var email: String = "",
                var name: String = "", var image: String = ""): Parcelable {
    private constructor(source: Parcel) :
            this(source.readString(), source.readString(), source.readString(), source.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(email)
        dest.writeString(name)
        dest.writeString(image)
    }

    override fun describeContents(): Int = 0

    companion object {
        val CREATOR = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User> = newArray(size)
        }
    }
}
