package com.raven.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raven.home.data.Result
import com.raven.home.domain.usecases.GeNewsUseCase
import com.raven.home.presentation.uistate.NewsUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   useCase: GeNewsUseCase
) : ViewModel() {

    val newUIState: StateFlow<NewsUIState> =
        useCase.execute("").map {
            when (it) {
                is Result.Error -> NewsUIState.ShowError(it.message)
                is Result.Success -> NewsUIState.DisplayNews(it.body)
            }
        }.catch {
            NewsUIState.ShowError(it.message.toString())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = NewsUIState.Loading
        )
}
