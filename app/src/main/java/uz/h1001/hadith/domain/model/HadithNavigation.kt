package uz.h1001.hadith.domain.model

import android.os.Parcel
import android.os.Parcelable

data class HadithNavigation(
    val number:Int,
    val title: String?,
    val description: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(number)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HadithNavigation> {
        override fun createFromParcel(parcel: Parcel): HadithNavigation {
            return HadithNavigation(parcel)
        }

        override fun newArray(size: Int): Array<HadithNavigation?> {
            return arrayOfNulls(size)
        }
    }
}