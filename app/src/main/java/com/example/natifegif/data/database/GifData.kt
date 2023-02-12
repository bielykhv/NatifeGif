package com.example.natifegif.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gif_list")
data class GifData(
    @PrimaryKey
    var id: String,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name="title")
    var title: String? = null
)
