package com.erzhan.mynote.domain.note

import com.erzhan.mynote.domain.time.DateTimeUtil

class SearchNote {

    fun execute(query: String, notes: List<Note>): List<Note> {
        if (query.isBlank()) {
            return notes
        }

        return notes.filter { note ->
            note.title.contains(query, true) || note.content.contains(query, true)
        }.sortedBy {
            DateTimeUtil.toEpochMillis(it.createdAt)
        }
    }


}