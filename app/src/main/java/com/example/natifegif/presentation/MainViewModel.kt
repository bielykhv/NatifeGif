package com.example.natifegif.presentation

import android.os.Environment
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.natifegif.data.database.DeletedItem
import com.example.natifegif.data.database.GifData
import com.example.natifegif.domain.*
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.ByteArrayBuffer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.*
import java.net.URL
import java.net.URLConnection
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val deleteGifFromDataBaseUseCase: DeleteGifFromDataBaseUseCase,
    private val getDataFromNetUseCase: GetDataFromNetUseCase,
    private val getDeletedGifListFromDb: GetDeletedGifListFromDb,
    private val getFilteredGifsUseCase: GetFilteredGifsUseCase,
    private val getGifListFromDataBaseUseCase: GetGifListFromDataBaseUseCase,
    private val insertGifToDataBaseUseCase: InsertGifToDataBaseUseCase,
    private val insertDeletedGifUseCase: InsertDeletedGifUseCase
) : ViewModel() {
    var state = MutableLiveData<String>()
    var philterModelList = MutableLiveData<List<GifData>>()
    private var offset = 0
    var gifList = MutableLiveData<List<GifData>>()

    fun getItem() = viewModelScope.launch {
        try {
            val gif = getDataFromNetUseCase.getDataFromNet("3", offset.toString())
            offset += 3
            gif.data?.forEach {
                val url = it.images?.original?.url
                saveGifIntoInternal(it.id!!, it.title!!, url!!)
            }

        } catch (e: Exception) {
            state.value = e.toString()
        }
        getGifList()
    }


    private fun getGifList() = viewModelScope.launch {
        gifList.value = getGifListFromDataBaseUseCase.getGifListFromDb()
    }

    fun stateValue() {
        state.value = ""
    }

    fun getToDb(title: String) = viewModelScope.launch {
        philterModelList.postValue(getFilteredGifsUseCase.getFilteredGifs(title))
    }

    fun deleteGif(id: String) = viewModelScope.launch {
        deleteGifFromDataBaseUseCase.deleteGifFormDb(id)
        getGifList()
    }

    fun insertDeleted(gifData: GifData) = viewModelScope.launch {
        val deletedItem = DeletedItem(id = gifData.id, url = gifData.url, title = gifData.title)
        insertDeletedGifUseCase.insertDeleted(deletedItem)
    }

    private fun saveGifIntoInternal(id: String, fileName: String, url: String) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val path =
                    Environment.getExternalStorageDirectory().toString() + "/download/natifeGif"
                val file = File(path)
                if (!file.exists()) {
                    file.mkdirs()
                }
                val ll = URL(url)
                val ucon: URLConnection = ll.openConnection()
                val `is`: InputStream = ucon.getInputStream()
                val bis = BufferedInputStream(`is`)
                val baf = ByteArrayBuffer(50)
                var current: Int
                while (bis.read().also { current = it } != -1) {
                    baf.append(current)
                }
                val outputFile = File(
                    file,
                    "$id$fileName.gif"
                )
                val fos = FileOutputStream(outputFile)
                fos.write(baf.toByteArray())
                fos.close()
                val deleteList = getDeletedGifListFromDb.getDeletedGifListFromDb()
                val gifData = GifData(id, "$path/$id$fileName.gif", fileName)
                if (deleteList.isEmpty()) {
                    insertGifToDataBaseUseCase.insertGifToDb(gifData)
                } else {
                    if (!deleteList.contains(gifData))
                        insertGifToDataBaseUseCase.insertGifToDb(gifData)
                }
                getGifList()
            } catch (e: IOException) {
                Log.d("TAG", "Error: $e")
            }
        }
}