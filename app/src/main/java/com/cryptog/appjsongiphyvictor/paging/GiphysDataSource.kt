package com.cryptog.appjsongiphyvictor.paging

import android.arch.paging.PageKeyedDataSource
import com.cryptog.appjsongiphyvictor.model.Giphy
import com.cryptog.appjsongiphyvictor.repository.GiphyRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy

internal class GiphysDataSource constructor(
        private val compositeDisposable: CompositeDisposable,
        private val query: String,
        private val giphyRepository: GiphyRepository
) : PageKeyedDataSource<Int, Giphy>() {


    override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, Giphy>
    ) {
        val quantityGiphys = params.requestedLoadSize
        createObservable(0, 0, quantityGiphys, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Giphy>) {
        val page = params.key
        val quantityGiphys = params.requestedLoadSize
        createObservable(page, page + 1, quantityGiphys, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Giphy>) {
        val page = params.key
        val quantityGiphys = params.requestedLoadSize
        createObservable(page, page - 1, quantityGiphys, null, callback)
    }

    private fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 requestedLoadSize: Int,
                                 initialCallback: LoadInitialCallback<Int, Giphy>?,
                                 callback: LoadCallback<Int, Giphy>?) {

        giphyRepository.getGiphys(query, requestedPage * requestedLoadSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {
                            initialCallback?.onResult(it.data, null, adjacentPage)
                            callback?.onResult(it.data, adjacentPage)
                        },
                        onError = { it.printStackTrace() }
                ).addTo(compositeDisposable)

    }
}