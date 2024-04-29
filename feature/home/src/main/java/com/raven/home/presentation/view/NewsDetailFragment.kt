package com.raven.home.presentation.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.raven.home.R
import com.raven.home.databinding.DetailNewsFragmentBinding
import com.raven.home.domain.models.ItemNews
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : Fragment() {

    private var _binding: DetailNewsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsItem: ItemNews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if (bundle != null) {
            newsItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                bundle.getSerializable("item", ItemNews::class.java)!!
            else
                bundle.getSerializable("item") as ItemNews
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailNewsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvNewsDetailTitle.text = newsItem.title
            tvNewsDetailByLine.text = newsItem.author
            tvNewsDetailPublishedDate.text = newsItem.publishedDate
            tvNewsDetailSubtitle.text = newsItem.subtitle
            Glide.with(binding.root.context)
                .load(newsItem.urlImage)
                .placeholder(R.drawable.place_holder_img)
                .into(binding.imgNewsPoster)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}