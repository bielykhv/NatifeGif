package com.example.natifegif.domain

import com.example.natifegif.data.database.GifData
import javax.inject.Inject

class InsertGifToDataBaseUseCase @Inject constructor(private val repository: Repository) {

    suspend fun insertGifToDb(gifData: GifData){
        repository.insertGif(gifData)
    }
}