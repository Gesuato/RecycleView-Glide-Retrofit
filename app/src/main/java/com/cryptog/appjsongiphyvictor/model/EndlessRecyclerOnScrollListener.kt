package com.cryptog.appjsongiphyvictor.model

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    /**
     * The total number of items in the dataset after the last load
     */
    private var mPreviousTotal = 0
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView!!, dx, dy)

        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = recyclerView.layoutManager!!.itemCount
        val mSpanCount = 2
        val into = IntArray(mSpanCount)
        val firstVisibleItem =
            (recyclerView.layoutManager as StaggeredGridLayoutManager).findFirstCompletelyVisibleItemPositions(
                into
            )[0]

        Log.d("totalItemCount", totalItemCount.toString())
        Log.d("visibleItemCount", visibleItemCount.toString())
        Log.d("firstVisibleItem", firstVisibleItem.toString())

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false
                mPreviousTotal = totalItemCount
            }
        }
        val visibleThreshold = 5
        if (!mLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
            // End has been reached
            onLoadMore()
            mLoading = true
        }
    }

    abstract fun onLoadMore()
}