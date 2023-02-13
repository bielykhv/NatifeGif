package com.example.natifegif.presentation

import androidx.lifecycle.*
import com.example.natifegif.domain.GetGifListFromDataBaseUseCase
import com.example.natifegif.data.database.GifData
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActivityViewModel @Inject constructor(private val getGifListFromDataBaseUseCase: GetGifListFromDataBaseUseCase): ViewModel() {

val gifList: LiveData<List<GifData>> = getGifListFromDataBaseUseCase.getGifListFromDb().asLiveData()



}