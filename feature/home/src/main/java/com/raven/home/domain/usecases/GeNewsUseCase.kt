package com.raven.home.domain.usecases


import com.raven.home.data.Result
import com.raven.core.bases.BaseUseCase
import com.raven.home.domain.models.ItemNews
import com.raven.home.domain.repository.HomeDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GeNewsUseCase @Inject constructor(
    private val dataSource: HomeDataSource
) : BaseUseCase<String, Result<List<ItemNews>>>() {
    override suspend fun execute(params: String): Flow<Result<List<ItemNews>>> =
        flow { dataSource.getNews() }

}
