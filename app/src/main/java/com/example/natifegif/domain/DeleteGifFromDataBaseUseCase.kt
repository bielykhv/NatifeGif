package com.example.natifegif.domain

import javax.inject.Inject

class DeleteGifFromDataBaseUseCase @Inject constructor(private val repository: Repository) {
         suspend fun deleteGifFormDb(id:String){
             repository.deleteGif(id)
         }

}