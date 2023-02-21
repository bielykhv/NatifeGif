package com.example.natifegif.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.natifegif.data.database.GifData
import com.example.natifegif.domain.GetGifListFromDataBaseUseCase
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
    private val getGifListFromDataBaseUseCase: GetGifListFromDataBaseUseCase
    ): ViewModel() {
    val gifList: LiveData<List<GifData>> by lazy {
        getGifListFromDataBaseUseCase.getGifListFromDb().asLiveData() }
}