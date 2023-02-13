package com.example.natifegif.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGif(gifData: GifData)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeleted(deletedItem: DeletedItem)

    @Query("DELETE  FROM gif_list WHERE id IS :id")
    suspend fun deleteGif(id: String)

    @Query("SELECT * FROM deleted")
    suspend fun getAllDeleted(): List<GifData>

    @Query("SELECT * FROM gif_list WHERE title LIKE :title")
    suspend fun getAllFilteredGifs(title: String): List<GifData>

    @Query("SELECT * FROM gif_list")
    fun getAllGifs():Flow<List<GifData>>


}