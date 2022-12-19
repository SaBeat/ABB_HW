package com.example.abb_note_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.abb_note_app.data.model.NoteEntity

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(noteEntity: NoteEntity)

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

    @Query("DELETE FROM TB_NOTE WHERE id = :noteId")
    fun deleteNoteById(noteId:Int)

    @Query("SELECT * FROM TB_NOTE")
    fun getAllNotes():LiveData<List<NoteEntity>>
}