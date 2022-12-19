package com.erzhan.mynote.data.note

import com.erzhan.mynote.domain.note.Note
import database.NoteEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        content = content,
        colorHex = colorHex.toLong(),
        createdAt = Instant
            .fromEpochMilliseconds(createdAt)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}