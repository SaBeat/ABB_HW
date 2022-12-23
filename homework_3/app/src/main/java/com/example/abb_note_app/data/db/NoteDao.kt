package com.example.abb_note_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.abb_note_app.data.model.NoteEntity

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Update
    suspend fun updateNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("DELETE FROM TB_NOTE")
    fun deleteAllList()

    @Query("SELECT * FROM TB_NOTE")
    fun getAllNotes():LiveData<List<NoteEntity>>

    @Query("SELECT * FROM TB_NOTE WHERE title LIKE '%' || :query || '%' or body LIKE '%' || :query || '%'")
    fun getNoteByKeyword(query:String):LiveData<List<NoteEntity>>
}