package com.example.natifegif.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted")
data class DeletedItem(
    @PrimaryKey
    var id: String,
    @ColumnInfo(name = "url")
    var url: String? = null,
    @ColumnInfo(name = "title")
    var title: String? = null

)