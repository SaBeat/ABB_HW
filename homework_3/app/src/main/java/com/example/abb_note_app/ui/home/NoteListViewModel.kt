package com.example.abb_note_app.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.abb_note_app.data.model.NoteEntity
import com.example.abb_note_app.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    fun getAllNotes():LiveData<List<NoteEntity>> = repository.getAllNotes()


    fun insertNote(noteEntity: NoteEntity) {
        viewModelScope.launch {
            repository.insertNote(noteEntity)
        }
    }

    fun deleteNote(noteEntity: NoteEntity){
        viewModelScope.launch {
            repository.deleteNote(noteEntity)
        }
    }

    fun deleteNoteById(noteId:Int){
        viewModelScope.launch {
            repository.deleteNoteById(noteId)
        }
    }
}