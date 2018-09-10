package com.cryptog.appjsongiphyvictor

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cryptog.appjsongiphyvictor.model.Giphy


class GiphyAdapter : RecyclerView.Adapter<GiphyAdapter.GiphyViewHolder>() {

    private val listGiphy = mutableListOf<Giphy>()
    private var displayWidth: Int = 0
    private var onCustomClickListener: CustomOnClickListener? = null

    fun update(listGiphy: MutableList<Giphy>) {
        val oldSize = this.listGiphy.size
        this.listGiphy.clear()
        this.listGiphy.addAll(listGiphy)
        this.displayWidth = displayWidth
        notifyItemRangeChanged(oldSize, listGiphy.size)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GiphyViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val itemView = layoutInflater.inflate(R.layout.view_holder, viewGroup, false)
        val viewholder = GiphyViewHolder(itemView)

        if (displayWidth == 0) {
            displayWidth = viewGroup.context.resources.displayMetrics.widthPixels
        }
        return viewholder
    }

    override fun getItemCount(): Int {
        return listGiphy.size
    }

    override fun onBindViewHolder(viewHolder: GiphyViewHolder, position: Int) {
        val currentGiphy = listGiphy[position]
        val currentUrl = currentGiphy.images.original.url
        val currentWidth: Double = (displayWidth.toDouble() / 2)
        val currentHidth: Double =
            (currentWidth / currentGiphy.images.original.width) * currentGiphy.images.original.height

        with(currentGiphy) {
            val layoutParams = ConstraintLayout.LayoutParams(
                currentWidth.toInt(),
                currentHidth.toInt()
            )
            viewHolder.itemView.layoutParams = layoutParams
        }

        with(viewHolder) {
            Glide
                .with(itemView.context)
                .load(currentUrl)
                .into(giphy)
        }

        viewHolder.itemView.setOnClickListener {
            onCustomClickListener?.onCustomItemClickListener(listGiphy[viewHolder.adapterPosition])
        }
    }

    fun setOnCustomItemClickListener(onItemClickListener: CustomOnClickListener) {
        onCustomClickListener = onItemClickListener
    }

    class GiphyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val giphy: ImageView = itemView.findViewById(R.id.imageGiphyId)

    }

}