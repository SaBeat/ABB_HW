package com.example.randomuser.data.db.contacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Contact")
data class DbContact(
    @PrimaryKey(autoGenerate = true) val userId: Int= 0,
    @ColumnInfo val firstName: String?,
    @ColumnInfo val lastName: String?,
    @ColumnInfo val email: String?,
    @ColumnInfo val photo: String?,
    @ColumnInfo val isDeleted:Boolean
)
