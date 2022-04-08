package com.example.litvinylwall.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Info::class], version = 2, exportSchema = false)
abstract class InfoDatabase : RoomDatabase() {

    abstract val infoDatabaseDao: InfoDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: InfoDatabase? = null

        fun getInstance(context: Context): InfoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        InfoDatabase::class.java,
                        "info_database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}