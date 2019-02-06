package com.cryptog.appjsongiphyvictor.Retrofit

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GiphyApi {
    @GET("/v1/gifs/search?")
    fun getGiphys(
        @Query("q") query: String,
        @Query("api_key") apiKey: String,
        @Query("offset") offset: Int? = 0
    ): Single<DataGiphy>

    companion object {
        const val API_KEY = "ZCz3h6y04yCAMUiG9GOzy59cm1fyW1L4"

        fun getService(): GiphyApi {
            val gson = GsonBuilder().setLenient().create()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.giphy.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            return retrofit.create(GiphyApi::class.java)
        }
    }
}