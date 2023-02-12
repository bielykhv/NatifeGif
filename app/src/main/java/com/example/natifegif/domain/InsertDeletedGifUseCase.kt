package com.example.natifegif.domain

import com.example.natifegif.data.database.DeletedItem
import javax.inject.Inject

class InsertDeletedGifUseCase @Inject constructor(private val repository: Repository) {
    suspend fun insertDeleted(deletedItem: DeletedItem){
        repository.insertDeletedGif(deletedItem)
    }
}