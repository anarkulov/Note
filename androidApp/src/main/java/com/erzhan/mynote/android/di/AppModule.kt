package com.erzhan.mynote.android.di

import android.app.Application
import com.erzhan.mynote.data.local.DatabaseDriverFactory
import com.erzhan.mynote.data.note.SqlDelightNoteDataSource
import com.erzhan.mynote.database.MyNoteDatabase
import com.erzhan.mynote.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(MyNoteDatabase(driver))
    }
}