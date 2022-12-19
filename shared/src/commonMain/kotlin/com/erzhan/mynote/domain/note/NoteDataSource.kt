package com.erzhan.mynote.domain.note

interface NoteDataSource {
    suspend fun insertNote(note: Note)
    suspend fun getNoteById(id: Long): Note?
    suspend fun getAllNotes(): List<Note>
    suspend fun addOrUpdateNote(note: Note)
    suspend fun deleteNoteById(id: Long)
}