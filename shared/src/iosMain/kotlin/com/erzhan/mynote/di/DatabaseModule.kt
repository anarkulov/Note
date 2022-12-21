package com.erzhan.mynote.di

import com.erzhan.mynote.data.local.DatabaseDriverFactory
import com.erzhan.mynote.data.note.SqlDelightNoteDataSource
import com.erzhan.mynote.database.MyNoteDatabase
import com.erzhan.mynote.domain.note.NoteDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(MyNoteDatabase(factory.createDriver()))
    }
}