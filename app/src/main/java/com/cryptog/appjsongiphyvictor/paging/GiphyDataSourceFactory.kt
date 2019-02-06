package com.cryptog.appjsongiphyvictor.paging

import android.arch.paging.DataSource
import com.cryptog.appjsongiphyvictor.Retrofit.GiphyApi
import com.cryptog.appjsongiphyvictor.model.Giphy
import com.cryptog.appjsongiphyvictor.repository.GiphyRepository
import io.reactivex.disposables.CompositeDisposable

internal class GiphyDataSourceFactory constructor(
    private val compositeDisposable: CompositeDisposable,
    private val query: String,
    private val giphyRepository: GiphyRepository
) : DataSource.Factory<Int, Giphy>() {

    override fun create(): DataSource<Int, Giphy> {
        return GiphysDataSource(compositeDisposable, query, giphyRepository)
    }
}