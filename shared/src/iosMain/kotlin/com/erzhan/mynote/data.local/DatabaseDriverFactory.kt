package com.erzhan.mynote.data.local

import com.erzhan.mynote.database.MyNoteDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {

    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MyNoteDatabase.Schema, "note.db")
    }

}