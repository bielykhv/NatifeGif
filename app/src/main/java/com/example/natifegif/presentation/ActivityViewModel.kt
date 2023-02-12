package com.example.natifegif.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natifegif.domain.GetGifListFromDataBaseUseCase
import com.example.natifegif.data.database.GifData
import kotlinx.coroutines.launch
import javax.inject.Inject

class ActivityViewModel @Inject constructor(private val getGifListFromDataBaseUseCase: GetGifListFromDataBaseUseCase): ViewModel() {
    var gifList = MutableLiveData<List<GifData>>()
    fun getGifList() = viewModelScope.launch {
        gifList.value = getGifListFromDataBaseUseCase.getGifListFromDb()
    }

}