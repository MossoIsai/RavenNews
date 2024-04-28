package com.raven.home.domain.usecases

import android.util.Log
import com.raven.core.bases.BaseUseCase
import com.raven.home.domain.repository.HomeDataSource
import com.raven.home.domain.models.ItemNews
import com.raven.home.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeNewsUseCase @Inject constructor(
    private val dataSource: HomeDataSource
) : BaseUseCase<Boolean, Result<List<ItemNews>>>() {
    override fun execute(params: Boolean): Flow<Result<List<ItemNews>>> =
        flow {
            dataSource.getNews(params).collect {
                when (it) {
                    is Result.Error -> emit(Result.Error(it.message))
                    is Result.Success -> emit(Result.Success(it.body?.items))
                }
            }
        }
}
