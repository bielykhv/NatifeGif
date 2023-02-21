package com.example.natifegif.data.network

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Gif {
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null
}