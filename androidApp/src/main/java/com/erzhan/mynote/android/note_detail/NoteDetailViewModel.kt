package com.erzhan.mynote.android.note_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erzhan.mynote.domain.note.Note
import com.erzhan.mynote.domain.note.NoteDataSource
import com.erzhan.mynote.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteDataSource: NoteDataSource,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val noteTitle = savedStateHandle.getStateFlow("noteTitle", "")
    private val isNoteTitleFocused = savedStateHandle.getStateFlow("isNoteTitleFocused", false)

    private val noteContent = savedStateHandle.getStateFlow("noteContent", "")
    private val isNoteContentFocused = savedStateHandle.getStateFlow("isNoteContentFocused", false)

    private val noteColor = savedStateHandle.getStateFlow("noteColor", Note.generateRandomColor())

    val state = combine(noteTitle,
        isNoteTitleFocused,
        noteContent,
        isNoteContentFocused,
        noteColor
    ) { noteTitle, isNoteTitleFocused, noteContent, isNoteContentFocused, noteColor ->
        NoteDetailState(
            noteTitle = noteTitle,
            isNoteTitleHintVisible = noteTitle.isEmpty() && !isNoteTitleFocused,
            noteContent = noteContent,
            isNoteContentHintVisible = noteContent.isEmpty() && !isNoteContentFocused,
            noteColor = noteColor
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteDetailState())

    private val _hasNoteBeenSaved = MutableStateFlow(false)
    val hasNoteBeenSaved = _hasNoteBeenSaved.asStateFlow()

    private var existingNoteId: Long? = null

    init {
        savedStateHandle.get<Long>("noteId")?.let { noteId ->
            if (noteId == -1L) {
                return@let
            }
            this.existingNoteId = noteId
            viewModelScope.launch {
                noteDataSource.getNoteById(noteId)?.let { note ->
                    savedStateHandle["noteTitle"] = note.title
                    savedStateHandle["noteContent"] = note.content
                    savedStateHandle["noteColor"] = note.colorHex
                }
            }
        }
    }

    fun onNoteTitleChanged(noteTitle: String) {
        savedStateHandle["noteTitle"] = noteTitle
    }

    fun onNoteTitleTextFocused(isNoteTitleFocused: Boolean) {
        savedStateHandle["isNoteTitleFocused"] = isNoteTitleFocused
    }

    fun onNoteContentChanged(noteContent: String) {
        savedStateHandle["noteContent"] = noteContent
    }

    fun onNoteContentTextFocused(isNoteContentFocused: Boolean) {
        savedStateHandle["isNoteContentFocused"] = isNoteContentFocused
    }

    fun onNoteColorChanged(noteColor: Int) {
        savedStateHandle["noteColor"] = noteColor
    }

    fun saveNote() {
        if (state.value.noteTitle.isEmpty() && state.value.noteContent.isEmpty()) {
            _hasNoteBeenSaved.value = true
            return
        }
        viewModelScope.launch {
            noteDataSource.insertNote(
                Note(
                    id = existingNoteId,
                    title = noteTitle.value,
                    content = noteContent.value,
                    colorHex = noteColor.value,
                    createdAt = DateTimeUtil.getCurrentDateTime()
                )
            )
            _hasNoteBeenSaved.value = true
        }
    }
}