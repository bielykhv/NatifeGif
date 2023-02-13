package com.example.natifegif.domain

import com.example.natifegif.data.database.GifData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGifListFromDataBaseUseCase @Inject constructor(private val repository: Repository) {

     fun getGifListFromDb():Flow<List<GifData>> {
        return repository.getListFromDb()
    }


}