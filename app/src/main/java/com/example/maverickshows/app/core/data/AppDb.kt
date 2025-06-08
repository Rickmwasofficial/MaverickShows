package com.example.maverickshows.app.core.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RecentSearchEntity::class], version = 1, exportSchema = false)
abstract class AppDb: RoomDatabase() {
    abstract fun recentDao(): RecentDao

    // allows access to the methods to create or get the database
    companion object {
        @Volatile
        private var Instance: AppDb? = null
        fun getDatabase(context: Context): AppDb {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDb::class.java, "app_database")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}