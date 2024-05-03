package com.raven.home.domain.usecases

import com.raven.core.bases.BaseUseCase
import com.raven.home.domain.repository.HomeDataSource
import com.raven.home.domain.models.ItemNews
import com.raven.home.data.Result
import com.raven.home.di.ServiceFactoryModule.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GeNewsUseCase @Inject constructor(
    private val dataSource: HomeDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, Result<List<ItemNews>>>() {
    override fun execute(params: Unit) = flow<Result<List<ItemNews>>> {
        dataSource.getNews().collect {
            when (it) {
                is Result.Error -> emit(Result.Error(it.message))
                is Result.Success -> emit(Result.Success(it.body?.items))
            }
        }
    }.flowOn(dispatcher)
}
