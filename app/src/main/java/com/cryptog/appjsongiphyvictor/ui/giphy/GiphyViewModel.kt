package com.cryptog.appjsongiphyvictor.ui.giphy

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.cryptog.appjsongiphyvictor.Retrofit.GiphyApi
import com.cryptog.appjsongiphyvictor.model.Giphy
import com.cryptog.appjsongiphyvictor.paging.GiphyDataSourceFactory
import com.cryptog.appjsongiphyvictor.repository.GiphyRepository
import io.reactivex.disposables.CompositeDisposable

class GiphyViewModel : ViewModel() {

    private lateinit var giphyList: LiveData<PagedList<Giphy>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 40
    private lateinit var sourceFactory: GiphyDataSourceFactory
    private val giphyRepository: GiphyRepository = GiphyRepository(GiphyApi.getService())

    fun subscribeToGiphyList(query: String): LiveData<PagedList<Giphy>> {
        sourceFactory = GiphyDataSourceFactory(compositeDisposable, query, giphyRepository)

        val config = PagedList.Config.Builder()
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(false)
                .build()

        giphyList = LivePagedListBuilder(sourceFactory, config).build()

        return giphyList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}