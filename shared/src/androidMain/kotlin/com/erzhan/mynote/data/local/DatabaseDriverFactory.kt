package com.erzhan.mynote.data.local

import android.content.Context
import com.erzhan.mynote.database.MyNoteDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {

    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MyNoteDatabase.Schema, context, "note.db")
    }

}