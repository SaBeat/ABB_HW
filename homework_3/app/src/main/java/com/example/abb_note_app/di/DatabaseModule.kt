package com.example.abb_note_app.di

import android.content.Context
import androidx.room.Room
import com.example.abb_note_app.data.db.NoteDao
import com.example.abb_note_app.data.db.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "db_notes"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideDatabaseDao(noteDatabase: NoteDatabase):NoteDao = noteDatabase.noteDao()
}