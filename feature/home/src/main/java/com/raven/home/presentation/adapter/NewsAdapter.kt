package com.raven.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raven.home.databinding.ItemLayoutNewsBinding
import com.raven.home.domain.models.ItemNews

class NewsAdapter : ListAdapter<ItemNews, NewsAdapter.NewsHolder>(MovieDiffCallback()) {

    inner class NewsHolder(val bind: ItemLayoutNewsBinding) :
        RecyclerView.ViewHolder(bind.root)

    class MovieDiffCallback : DiffUtil.ItemCallback<ItemNews>() {
        override fun areItemsTheSame(oldItem: ItemNews, newItem: ItemNews): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemNews, newItem: ItemNews): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val itemBinding =
            ItemLayoutNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item = getItem(position)
        holder.bind.tvTitleNews.text = item.title
        holder.bind.tvSubtitleNews.text = item.subtitle
        holder.bind.tvCopyrightNews.text = item.copyright
        Glide.with(holder.bind.root.context).load(item.urlImage).into(holder.bind.posterNewsImg);
    }
}