package com.cryptog.appjsongiphyvictor.ui.giphy

import android.arch.lifecycle.ViewModel
import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import com.cryptog.appjsongiphyvictor.Retrofit.GiphyServer
import com.cryptog.appjsongiphyvictor.model.Giphy
import com.cryptog.appjsongiphyvictor.paging.GiphyDataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class GiphyViewModel : ViewModel() {

    private lateinit var giphyList: io.reactivex.Observable<PagedList<Giphy>>
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 20
    private lateinit var sourceFactory: GiphyDataSourceFactory

    fun subscribeToGiphyList(query: String): io.reactivex.Observable<PagedList<Giphy>> {
        sourceFactory =
                GiphyDataSourceFactory(compositeDisposable, GiphyServer.getService(), query)

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        giphyList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()

        return giphyList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}