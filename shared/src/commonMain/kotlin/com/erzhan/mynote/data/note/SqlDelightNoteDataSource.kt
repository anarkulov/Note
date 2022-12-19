package com.erzhan.mynote.data.note

import com.erzhan.mynote.database.MyNoteDatabase
import com.erzhan.mynote.domain.note.Note
import com.erzhan.mynote.domain.note.NoteDataSource
import com.erzhan.mynote.domain.time.DateTimeUtil

class SqlDelightNoteDataSource(
    db: MyNoteDatabase,
) : NoteDataSource {

    private val noteQueries = db.noteQueries

    override suspend fun insertNote(note: Note) {
        noteQueries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex.toString(),
            createdAt = DateTimeUtil.toEpochMillis(note.createdAt)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return noteQueries.getNoteById(id).executeAsOneOrNull()?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return noteQueries.getAllNotes().executeAsList().map { it.toNote() }
    }

    override suspend fun addOrUpdateNote(note: Note) {
        return noteQueries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex.toString(),
            createdAt = DateTimeUtil.toEpochMillis(note.createdAt)
        )
    }

    override suspend fun deleteNoteById(id: Long) {
        return noteQueries.deleteNoteById(id)
    }
}