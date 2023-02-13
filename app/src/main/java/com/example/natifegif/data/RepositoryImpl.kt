package com.example.natifegif.data

import com.example.natifegif.data.database.Dao
import com.example.natifegif.data.database.DeletedItem
import com.example.natifegif.data.network.Gif
import com.example.natifegif.data.database.GifData
import com.example.natifegif.data.network.ApiService
import com.example.natifegif.domain.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiService: ApiService, private val mainDb: Dao): Repository {
    override suspend fun deleteGif(id: String) {
       mainDb.deleteGif(id)
    }

    override suspend fun insertGif(gifData: GifData) {
        mainDb.insertGif(gifData)
    }

    override suspend fun insertDeletedGif(deletedItem: DeletedItem) {
        mainDb.insertDeleted(deletedItem)
    }

    override fun getListFromDb(): Flow<List<GifData>> {
        return mainDb.getAllGifs()
    }


    override suspend fun getDeletedGifListFromDb(): List<GifData> {
        return mainDb.getAllDeleted()
    }

    override suspend fun getDataFromNet(count: String, offset: String): Gif {
        return apiService.getByCount(count,offset)
    }

    override suspend fun getAllFilteredGifs(name: String): List<GifData> {
        return  mainDb.getAllFilteredGifs(name)
    }


}