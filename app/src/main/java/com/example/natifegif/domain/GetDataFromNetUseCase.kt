package com.example.natifegif.domain

import com.example.natifegif.data.network.Gif
import javax.inject.Inject

class GetDataFromNetUseCase @Inject constructor(private val repository: Repository) {
    suspend fun getDataFromNet(count:Int, offset:Int): Gif {
        return repository.getDataFromNet(count,offset)
    }
}