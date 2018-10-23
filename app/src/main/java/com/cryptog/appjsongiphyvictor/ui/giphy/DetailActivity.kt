package com.cryptog.appjsongiphyvictor.ui.giphy

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.cryptog.appjsongiphyvictor.R
import com.cryptog.appjsongiphyvictor.model.Giphy
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    var giphy: Giphy? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        giphy = intent.getSerializableExtra("CurrentGiphy") as Giphy
        giphy?.images?.original?.url?.let {
            Glide.with(this)
                .load(it)
                .into(imageView)
        }
    }
}
