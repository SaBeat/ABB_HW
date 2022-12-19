package com.example.abb_note_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.abb_note_app.data.model.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao() : NoteDao
}