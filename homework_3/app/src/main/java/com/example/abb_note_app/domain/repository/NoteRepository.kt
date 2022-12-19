package com.example.abb_note_app.domain.repository

import androidx.lifecycle.LiveData
import com.example.abb_note_app.data.db.NoteDao
import com.example.abb_note_app.data.model.NoteEntity
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {

    suspend fun insertNote(noteEntity: NoteEntity) = noteDao.insertNote(noteEntity)
    suspend fun deleteNote(noteEntity: NoteEntity) = noteDao.deleteNote(noteEntity)
    fun getAllNotes():LiveData<List<NoteEntity>> = noteDao.getAllNotes()
    fun deleteNoteById(noteId:Int) = noteDao.deleteNoteById(noteId)

}