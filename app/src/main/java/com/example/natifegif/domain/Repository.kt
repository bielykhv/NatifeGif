package com.example.natifegif.domain

import com.example.natifegif.data.database.DeletedItem
import com.example.natifegif.data.database.GifData
import com.example.natifegif.data.network.Gif
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun deleteGif(id:String)
    suspend fun insertGif(gifData: GifData)
    suspend fun insertDeletedGif(deletedItem: DeletedItem)
      fun getListFromDb():Flow<List<GifData>>
    suspend fun getDeletedGifListFromDb(): List<GifData>
    suspend fun getDataFromNet(count:String, offset:String): Gif
    suspend fun getAllFilteredGifs(name:String):List<GifData>

}