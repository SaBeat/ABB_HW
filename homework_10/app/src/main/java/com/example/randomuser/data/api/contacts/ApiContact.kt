package com.example.randomuser.data.api.contacts

import com.example.randomuser.data.db.contacts.DbContact
import com.google.gson.annotations.SerializedName

data class ApiContact(
    @SerializedName("name")
    val name: Name?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("picture")
    val picture: Picture?
)

data class Name(
    @SerializedName("first")
    val firstName: String?,
    @SerializedName("last")
    val lastName: String?,
    val picture: Picture?
)

data class Picture(
    val large: String?,
    val medium: String?,
    val thumbnail: String?
)

fun ApiContact.toDbContact(): DbContact {
    return DbContact(
        firstName = name?.firstName,
        lastName = name?.lastName,
        email = email,
        photo = picture?.thumbnail
    )
}