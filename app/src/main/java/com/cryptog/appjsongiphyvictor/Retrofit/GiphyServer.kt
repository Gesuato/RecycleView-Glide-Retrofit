package com.cryptog.appjsongiphyvictor.Retrofit

import com.cryptog.appjsongiphyvictor.DataGiphy
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyServer {
    @GET("/v1/gifs/search?")
    fun data(@Query("q") query: String,
             @Query("api_key") apiKey: String,
             @Query("offset") offset: String,
             @Query("limit") limit: String) : Call<DataGiphy>


}