package com.raven.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.raven.home.domain.usecases.GeNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNewsUseCase: GeNewsUseCase
):ViewModel() {


}
