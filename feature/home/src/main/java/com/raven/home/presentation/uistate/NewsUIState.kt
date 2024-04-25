package com.raven.home.presentation.uistate

import com.raven.home.domain.models.ItemNews

sealed class NewsUIState {
    object Loading : NewsUIState()
    data class ShowError(val errorMessage: String) : NewsUIState()
    data class DisplayNews(val newsList: List<ItemNews>?) : NewsUIState()
}