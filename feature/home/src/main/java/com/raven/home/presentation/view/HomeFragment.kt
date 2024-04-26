package com.raven.home.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raven.home.databinding.HomeFragmentBinding
import com.raven.home.domain.models.ItemNews
import com.raven.home.presentation.adapter.NewsAdapter
import com.raven.home.presentation.uistate.NewsUIState
import com.raven.home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private val loader by lazy { Loading() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newUIState.collect { uiState ->
                    when (uiState) {
                        is NewsUIState.DisplayNews -> displayMoviesList(uiState.newsList)
                        NewsUIState.Loading -> showLoader()
                        is NewsUIState.ShowError -> Log.d("", "")
                    }
                }
            }
        }
    }

    private fun displayMoviesList(newsList: List<ItemNews>?) {
        hideLoader()
        with(binding) {
            rvNewsList.visibility = View.VISIBLE
            newsAdapter = NewsAdapter()
            newsAdapter.submitList(newsList)
            rvNewsList.layoutManager = LinearLayoutManager(
                activity,
                RecyclerView.VERTICAL,
                false
            )
            rvNewsList.setHasFixedSize(true)
            rvNewsList.adapter = newsAdapter
        }
    }

    private fun showLoader() {
        loader.show(childFragmentManager, "")
    }

    private fun hideLoader() {
        loader.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
