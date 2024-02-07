package com.lucasaugustocastro.animejotter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lucasaugustocastro.animejotter.databinding.SearchedItemViewBinding
import com.lucasaugustocastro.animejotter.models.AnimeDTO
import com.squareup.picasso.Picasso

class SearchAdapter(private val items: List<AnimeDTO> ): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private var onClickListener: OnClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = SearchedItemViewBinding.inflate(
            inflater,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.textView.text = item.title
        holder.binding.title = item
        Picasso.get().load(item.images?.jpg?.imageUrl).into(holder.binding.imgTitle)

        holder.itemView.setOnClickListener{
            if (onClickListener != null) {
                onClickListener!!.onClick(position, item )
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: AnimeDTO)
    }

    inner class ViewHolder (val binding: SearchedItemViewBinding): RecyclerView.ViewHolder(binding.root)
}