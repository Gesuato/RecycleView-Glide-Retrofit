package com.cryptog.appjsongiphyvictor.Retrofit

import com.cryptog.appjsongiphyvictor.model.DataGiphy
import io.reactivex.Single

 class GiphyRepository {

    fun loadGiphy(query: String): Single<DataGiphy> {
        return GiphyServer.getService().data(query, GiphyServer.API_KEY)
    }
}