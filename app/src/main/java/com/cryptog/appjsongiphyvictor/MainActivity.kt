package com.cryptog.appjsongiphyvictor

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.cryptog.appjsongiphyvictor.Retrofit.GiphyRepository
import com.cryptog.appjsongiphyvictor.model.Giphy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val listGiphy: MutableList<Giphy> = ArrayList()
    private var listGiphyAdapter = GiphyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentG = Intent(this, DetailActivity::class.java)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        progressBar.visibility = View.VISIBLE
        recyclerViewGiphy.layoutManager = layoutManager
        recyclerViewGiphy.adapter = this.listGiphyAdapter
        this.listGiphyAdapter.setOnCustomItemClickListener(object : CustomOnClickListener {
            override fun onCustomItemClickListener(giphy: Giphy) {
                intentG.putExtra("CurrentGiphy", giphy)
                startActivity(intentG)
            }
        })
        getData()
    }

    @SuppressLint("CheckResult")
    private fun getData() {
        progressBar.visibility = View.VISIBLE
        this.listGiphy.clear()

        val repository = GiphyRepository()
        repository.loadGiphy("Batman")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy {
                this.listGiphy.addAll(it.data)
                this.listGiphyAdapter.update(this.listGiphy)
                this.progressBar.visibility = View.GONE
            }

    }
}
