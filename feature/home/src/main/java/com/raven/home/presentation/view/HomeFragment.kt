package com.raven.home.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.raven.home.R
import com.raven.home.data.ConnectivityObserver
import com.raven.home.databinding.HomeFragmentBinding
import com.raven.home.domain.models.ItemNews
import com.raven.home.presentation.adapter.NewsAdapter
import com.raven.home.presentation.uistate.NewsUIState
import com.raven.home.presentation.viewmodel.HomeViewModel
import com.raven.home.presentation.viewmodel.ItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private val viewModelActivity: ItemViewModel by activityViewModels()

    private lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewLifecycleOwner.lifecycleScope.launch {
            connectivityObserver.observerNetwork().collect {
                if (!isNetworkAvailable(it)) {
                    SnackBarMessage.make(
                        binding.clMainContainer,
                        getString(R.string.with_out_connection_network)
                    ).show()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newUIState.collect { uiState ->
                    when (uiState) {
                        is NewsUIState.DisplayNews -> displayMoviesList(uiState.newsList)
                        NewsUIState.Loading -> showLoader()
                        is NewsUIState.ShowError -> {
                            showError(uiState.errorMessage)
                        }

                        NewsUIState.EmptyList -> emptyState()
                    }
                }
            }
        }
    }

    private fun displayMoviesList(newsList: List<ItemNews>?) {
        with(binding) {
            sfLayout.visibility = View.GONE
            rvNewsList.visibility = View.VISIBLE
            clEmptyStateLayout.visibility = View.GONE
            newsAdapter = NewsAdapter(::goToDetail)
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

    private fun isNetworkAvailable(connectionStatus: ConnectivityObserver.NetworkStatus): Boolean {
        return when (connectionStatus) {
            ConnectivityObserver.NetworkStatus.Available -> true
            ConnectivityObserver.NetworkStatus.Unavailable -> false
            ConnectivityObserver.NetworkStatus.Losing -> false
            ConnectivityObserver.NetworkStatus.Lost -> false
        }
    }

    private fun showError(error: String) {
        with(binding) {
            sfLayout.visibility = View.GONE
            SnackBarMessage.make(clMainContainer, error)
                .show()
        }
    }

    private fun showLoader() {
        with(binding) {
            sfLayout.visibility = View.VISIBLE
            rvNewsList.visibility = View.GONE
            clEmptyStateLayout.visibility = View.GONE
            sfLayout.startShimmer()
        }
    }

    private fun emptyState() {
        with(binding) {
            sfLayout.visibility = View.GONE
            rvNewsList.visibility = View.GONE
            clEmptyStateLayout.visibility = View.VISIBLE
        }
    }

    private fun goToDetail(news: ItemNews) {
        viewModelActivity.selectItem(news)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
