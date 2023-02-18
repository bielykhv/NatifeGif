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
        private const val DB_NAME = "main_db"

        fun getDataBase(context: Context): MainDb {
            INSTANCE?.let {
                return it
            }
            synchronized(this){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(context.applicationContext,MainDb::class.java, DB_NAME)
                    .build()
                INSTANCE = db
                return db
            }

        }
    }
}