package com.raven.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raven.home.domain.models.ItemNews

class ItemViewModel : ViewModel() {
    private val mutableSelectedItem = MutableLiveData<ItemNews>()
    val selectedItem: LiveData<ItemNews> get() = mutableSelectedItem
    fun selectItem(item: ItemNews) {
        mutableSelectedItem.value = item
    }
}