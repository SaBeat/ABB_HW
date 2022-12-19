package com.example.abb_note_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_note")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val body: String
){
    constructor() : this(0, "","")
}
