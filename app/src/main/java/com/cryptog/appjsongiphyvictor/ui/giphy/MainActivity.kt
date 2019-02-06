package com.cryptog.appjsongiphyvictor.ui.giphy

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.cryptog.appjsongiphyvictor.CustomOnClickListener
import com.cryptog.appjsongiphyvictor.R
import com.cryptog.appjsongiphyvictor.adapters.GiphyAdapter
import com.cryptog.appjsongiphyvictor.model.Giphy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listGiphyAdapter = GiphyAdapter()
    private lateinit var viewModel: GiphyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentG = Intent(this, DetailActivity::class.java)
        viewModel = ViewModelProviders.of(this).get(GiphyViewModel::class.java)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        progressBar.visibility = View.VISIBLE
        recyclerViewGiphy.layoutManager = layoutManager
        recyclerViewGiphy.adapter = this.listGiphyAdapter

        this.listGiphyAdapter.setOnCustomItemClickListener(object :
                CustomOnClickListener {
            override fun onCustomItemClickListener(giphy: Giphy) {
                intentG.putExtra(FLAG_CURRENT_GIPHY, giphy)
                startActivity(intentG)
            }
        })
        subscribeToGiphyList("Superman")
    }

    private fun subscribeToGiphyList(query: String) {
        progressBar.visibility = View.VISIBLE
        viewModel.subscribeToGiphyList(query).observe(this, Observer {
            listGiphyAdapter.submitList(it)
            progressBar.visibility = View.GONE

        })
    }

    companion object {
        const val FLAG_CURRENT_GIPHY = "FLAG_CURRENT_GIPHY"
    }
}
