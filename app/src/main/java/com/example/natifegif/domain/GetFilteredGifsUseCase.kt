package com.example.natifegif.domain

import com.example.natifegif.data.database.GifData
import javax.inject.Inject

class GetFilteredGifsUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getFilteredGifs(name:String):List<GifData>{
        return repository.getAllFilteredGifs(name)
    }
}