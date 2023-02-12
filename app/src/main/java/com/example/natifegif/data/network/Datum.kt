package com.example.natifegif.data.network


import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class Datum {


    @SerializedName("id")
    @Expose
    var id: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null


    @SerializedName("images")
    @Expose
    var images: Images? = null


}