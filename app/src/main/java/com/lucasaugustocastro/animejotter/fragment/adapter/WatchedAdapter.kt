package com.lucasaugustocastro.animejotter.fragment.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasaugustocastro.animejotter.entities.WatchedTitle
import com.lucasaugustocastro.animejotter.data.DataStore
import com.lucasaugustocastro.animejotter.databinding.WatchedItemViewBinding
import com.squareup.picasso.Picasso

class WatchedAdapter(private val items: List<WatchedTitle>, private val longPressListener: (WatchedTitle) -> Unit ): RecyclerView.Adapter<WatchedAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = WatchedItemViewBinding.inflate(
            inflater,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        Picasso.get().load(item.title_image).into(holder.binding.imgTitle)
        holder.binding.title = item
        holder.itemView.setOnClickListener{
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }

        holder.binding.imgFavorite.setOnClickListener{
            DataStore.setFavorite(position, false)
            this.notifyDataSetChanged()
        }
        holder.itemView.setOnLongClickListener {
            longPressListener.invoke(item)
            true
        }
    }

    override fun getItemCount(): Int = items.size

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: WatchedTitle)
    }

    inner class ViewHolder (val binding: WatchedItemViewBinding): RecyclerView.ViewHolder(binding.root)
}