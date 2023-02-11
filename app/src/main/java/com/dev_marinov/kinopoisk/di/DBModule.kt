package com.dev_marinov.kinopoisk.di

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.dev_marinov.kinopoisk.data.local.db.AppDatabase
import com.dev_marinov.kinopoisk.data.local.db.KinopoiskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {

    @Provides
    @Singleton
    fun provideKinopoiskDao(appDatabase: AppDatabase) : KinopoiskDao {
        return appDatabase.kinopoiskDao()
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context) : AppDatabase {
        val db = databaseBuilder(
            context,
            AppDatabase::class.java, "database-kinopoisk"
        ).fallbackToDestructiveMigration().build()
        return db
    }

}