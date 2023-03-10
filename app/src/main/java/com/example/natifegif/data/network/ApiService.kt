package com.example.natifegif.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("trending?api_key=YGHnKKBGSydS6nSt6WAoUcICWwmgCfvL")
   suspend  fun getByCount(
        @Query(QUERY_PARAM_LIMIT) limit: Int,
        @Query(QUERY_PARAM_OFFSET) offset: Int
    ): Gif

    companion object{
       private const val QUERY_PARAM_LIMIT = "limit"
       private const val QUERY_PARAM_OFFSET = "offset"
    }
}