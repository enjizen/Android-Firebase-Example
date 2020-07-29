package com.tua.wanchalerm.androidfirebaseexample.model

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

data class User (
    val name: String?,
    val email: String?,
    val photoUrl: Uri?,
    val uid: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Uri::class.java.classLoader),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeParcelable(photoUrl, flags)
        parcel.writeString(uid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}