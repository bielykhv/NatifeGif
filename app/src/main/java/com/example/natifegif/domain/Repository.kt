package com.example.natifegif.domain

import com.example.natifegif.data.database.DeletedItem
import com.example.natifegif.data.database.GifData
import com.example.natifegif.data.network.Gif

interface Repository {
    suspend fun deleteGif(id:String)
    suspend fun insertGif(gifData: GifData)
    suspend fun insertDeletedGif(deletedItem: DeletedItem)
    suspend  fun getListFromDb(): List<GifData>
    suspend fun getDeletedGifListFromDb(): List<GifData>
    suspend fun getDataFromNet(count:String, offset:String): Gif
    suspend fun getAllFilteredGifs(name:String):List<GifData>

}