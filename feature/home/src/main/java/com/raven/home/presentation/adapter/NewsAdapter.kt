package com.raven.home.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.raven.home.R
import com.raven.home.databinding.ItemLayoutNewsBinding
import com.raven.home.domain.models.ItemNews

class NewsAdapter(
    private val onClickNews: (ItemNews) -> Unit
) : ListAdapter<ItemNews, NewsAdapter.NewsHolder>(NewsDiffCallback()) {

    inner class NewsHolder(
        private val binding: ItemLayoutNewsBinding,
        private val onClickNews: (ItemNews) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(news: ItemNews) {
            binding.tvTitleNews.text = news.title
            binding.tvSubtitleNews.text = news.subtitle
            binding.tvNewsByLine.text = news.author
            Glide.with(binding.root.context)
                .load(news.urlImage)
                .placeholder(R.drawable.place_holder_img)
                .into(binding.posterNewsImg)
            binding.root.setOnClickListener {
                onClickNews(news)
            }
        }
    }

    class NewsDiffCallback : DiffUtil.ItemCallback<ItemNews>() {
        override fun areItemsTheSame(oldItem: ItemNews, newItem: ItemNews): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ItemNews, newItem: ItemNews): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val itemBinding =
            ItemLayoutNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsHolder(itemBinding, onClickNews)
    }

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}