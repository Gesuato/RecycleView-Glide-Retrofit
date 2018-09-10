package com.cryptog.appjsongiphyvictor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import android.view.View
import com.cryptog.appjsongiphyvictor.Retrofit.GiphyRetrofit
import com.cryptog.appjsongiphyvictor.Retrofit.GiphyServer
import com.cryptog.appjsongiphyvictor.model.EndlessRecyclerOnScrollListener
import com.cryptog.appjsongiphyvictor.model.Giphy
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val listGiphy = mutableListOf<Giphy>()
    private var listGiphyAdapter = GiphyAdapter()
    private var limit = 20
    private var offset = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentG = Intent(this, DetailActivity::class.java)
        var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        progressBar.visibility = View.VISIBLE
        getData()

        recyclerViewGiphy.layoutManager = layoutManager
        listGiphyAdapter.update(listGiphy)
        recyclerViewGiphy.adapter = listGiphyAdapter
        listGiphyAdapter.setOnCustomItemClickListener(object : CustomOnClickListener {
            override fun onCustomItemClickListener(giphy: Giphy) {
                intentG.putExtra("CurrentGiphy", giphy)
                startActivity(intentG)
            }
        })
        recyclerViewGiphy.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                limit += 20
                getData()
            }
        })
    }

    fun getData() {
        progressBar.visibility = View.VISIBLE
        listGiphy.clear()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.giphy.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: GiphyServer = retrofit.create<GiphyServer>(GiphyServer::class.java)

        val requestData = service.data(
            "ryan+gosling",
            "ZCz3h6y04yCAMUiG9GOzy59cm1fyW1L4",
            offset.toString(),
            limit.toString()
        )

        requestData.enqueue(object : Callback<DataGiphy> {

            override fun onResponse(call: Call<DataGiphy>, response: Response<DataGiphy>) {

                if (response.isSuccessful) {
                    val dataJSON = response.body()
                    dataJSON?.run {

                        for (dataGiphy in this.data) {
                            val giphyRetrofit = GiphyRetrofit(dataGiphy.images)
                            val giphy = Giphy(giphyRetrofit.images)
//                            Log.v("OnResponse", giphy.images.original.height.toString())
                            listGiphy.add(giphy)
                        }
                        progressBar.visibility = View.GONE

                        if (listGiphy.size > 0) {
                            listGiphyAdapter.update(listGiphy)
                        }
                    }

                } else {
                    Log.v("OnResponse", "Reponse Error")
                }
            }

            override fun onFailure(call: Call<DataGiphy>, t: Throwable) {
                println("Erro call DataJSON")
                Log.v("OnResponse", t.message)
            }
        })
    }
}