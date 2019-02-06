package com.cryptog.appjsongiphyvictor.repository

import android.util.Log
import com.cryptog.appjsongiphyvictor.Retrofit.DataGiphy
import com.cryptog.appjsongiphyvictor.Retrofit.GiphyApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

internal class GiphyRepository(private val giphyApi: GiphyApi) {

    fun getGiphys(query: String, offSet: Int): Single<DataGiphy> {
        return giphyApi.getGiphys(query, GiphyApi.API_KEY, offSet)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { Log.d("getGiphys", "getGiphys Sucess") }
                .doOnError { Log.d("getGiphys", "getGiphys Error") }
    }
}