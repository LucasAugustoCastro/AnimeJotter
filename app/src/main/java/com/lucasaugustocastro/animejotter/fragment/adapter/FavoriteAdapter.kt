package com.lucasaugustocastro.animejotter.fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasaugustocastro.animejotter.entities.WatchedTitle
import com.lucasaugustocastro.animejotter.data.DataStore
import com.lucasaugustocastro.animejotter.databinding.WatchedItemViewBinding
import com.squareup.picasso.Picasso

class FavoriteAdapter(private val items: List<WatchedTitle> ): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
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
        holder.binding.title = item
        Picasso.get().load(item.title_image).into(holder.binding.imgTitle)

        holder.itemView.setOnClickListener{
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
        holder.binding.imgFavorite.setOnClickListener{
            DataStore.setFavorite(position, true)
            DataStore.favoriteTitles.removeAt(position)

            this.notifyDataSetChanged()
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