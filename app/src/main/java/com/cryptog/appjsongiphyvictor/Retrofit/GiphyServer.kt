package com.cryptog.appjsongiphyvictor.Retrofit

import com.cryptog.appjsongiphyvictor.model.DataGiphy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GiphyServer {
    @GET("/v1/gifs/search?")
    fun data(
        @Query("q") query: String,
        @Query("api_key") apiKey: String,
        @Query("offset") offset: Int? = 0
    ): Observable<DataGiphy>

    companion object {
        const val API_KEY = "ZCz3h6y04yCAMUiG9GOzy59cm1fyW1L4"

        fun getService(): GiphyServer {
            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.giphy.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(GiphyServer::class.java)
        }
    }
}