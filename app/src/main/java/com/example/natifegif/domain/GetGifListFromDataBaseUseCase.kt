package com.example.natifegif.domain

import com.example.natifegif.data.database.GifData
import javax.inject.Inject

class GetGifListFromDataBaseUseCase @Inject constructor(private val repository: Repository) {

    suspend fun getGifListFromDb():List<GifData> {
        return repository.getListFromDb()
    }


}