package com.cryptog.appjsongiphyvictor.adapters

import android.arch.paging.PagedListAdapter
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cryptog.appjsongiphyvictor.CustomOnClickListener
import com.cryptog.appjsongiphyvictor.R
import com.cryptog.appjsongiphyvictor.model.Giphy


class GiphyAdapter : PagedListAdapter<Giphy, GiphyAdapter.GiphyViewHolder>(giphyDif) {
    private var displayWidth: Int = 0
    private var onCustomClickListener: CustomOnClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GiphyViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.view_holder, viewGroup, false)
        val viewHolder = GiphyViewHolder(itemView)

        if (displayWidth == 0) {
            displayWidth = viewGroup.context.resources.displayMetrics.widthPixels
        }
        return viewHolder
    }


    override fun onBindViewHolder(viewHolder: GiphyViewHolder, position: Int) {
        val currentGiphy = getItem(position) ?: return
        val currentUrl = currentGiphy.images.original.url
        val currentWidth: Double = (displayWidth.toDouble() / 2)
        val currentHeight = (currentWidth / currentGiphy.images.original.width) * currentGiphy.images.original.height

        val layoutParams = ConstraintLayout.LayoutParams(
            currentWidth.toInt(),
            currentHeight.toInt()
        )
        viewHolder.itemView.layoutParams = layoutParams
        
        with(viewHolder) {
            Glide
                .with(itemView.context)
                .load(currentUrl)
                .into(giphy)
        }

        viewHolder.itemView.setOnClickListener {
            onCustomClickListener?.onCustomItemClickListener(getItem(viewHolder.adapterPosition)!!)
        }
    }

    fun setOnCustomItemClickListener(onItemClickListener: CustomOnClickListener) {
        onCustomClickListener = onItemClickListener
    }

    class GiphyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val giphy: ImageView = itemView.findViewById(R.id.imageGiphyId)
    }

    companion object {
        val giphyDif = object : DiffUtil.ItemCallback<Giphy>() {
            override fun areItemsTheSame(old: Giphy, new: Giphy): Boolean {
                return old.images.original.url == new.images.original.url
            }

            override fun areContentsTheSame(old: Giphy, new: Giphy): Boolean {
                return old == new
            }

        }
    }

}