package com.example.randomuser.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomuser.data.db.contacts.ContactsDao
import com.example.randomuser.data.db.contacts.DbContact

@Database(entities = [DbContact::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ContactsDao
}
