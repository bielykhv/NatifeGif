package com.example.natifegif.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GifData::class, DeletedItem::class], version = 1, exportSchema = false)
abstract class MainDb : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: MainDb? = null
        fun getDataBase(context: Context): MainDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, MainDb::class.java, "main_db"
                ).build()
                instance

            }

        }
    }
}