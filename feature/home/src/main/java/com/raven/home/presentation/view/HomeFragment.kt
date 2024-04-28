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
import com.raven.home.R
import com.raven.home.data.ConnectivityObserver
import com.raven.home.databinding.HomeFragmentBinding
import com.raven.home.domain.models.ItemNews
import com.raven.home.presentation.adapter.NewsAdapter
import com.raven.home.presentation.uistate.NewsUIState
import com.raven.home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter
    private var newtWorkStatus: Boolean = false

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

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launch {
            connectivityObserver.observerNetwork().collect {
                newtWorkStatus = isNetworkAvailable(it)
                if (!newtWorkStatus) {
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
                        is NewsUIState.ShowError -> showError(uiState.errorMessage)
                        NewsUIState.EmptyList -> Log.d("EMPTY", "EMPTY")
                    }
                }
            }
        }
    }

    private fun displayMoviesList(newsList: List<ItemNews>?) {
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

    private fun isNetworkAvailable(connectionStatus: ConnectivityObserver.NetworkStatus): Boolean {
        return when (connectionStatus) {
            ConnectivityObserver.NetworkStatus.Available -> true
            ConnectivityObserver.NetworkStatus.Unavailable -> false
            ConnectivityObserver.NetworkStatus.Losing -> false
            ConnectivityObserver.NetworkStatus.Lost -> false
        }
    }

    private fun showError(error: String) {
        SnackBarMessage.make(binding.clMainContainer, error)
            .show()
    }

    private fun showLoader() {
        SnackBarMessage.make(binding.clMainContainer, "LOADER")
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
