package com.example.natifegif.domain

import com.example.natifegif.data.database.GifData
import javax.inject.Inject

class GetDeletedGifListFromDb @Inject constructor(private val repository: Repository) {
    suspend fun getDeletedGifListFromDb():List<GifData>{
        return repository.getDeletedGifListFromDb()
    }
}