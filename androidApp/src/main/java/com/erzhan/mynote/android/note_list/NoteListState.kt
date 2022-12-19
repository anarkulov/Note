package com.erzhan.mynote.android.note_list

import com.erzhan.mynote.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
) {

}