package com.raven.home.data.repository

import com.raven.home.data.Result
import com.raven.home.data.remote.ServiceFactory
import com.raven.home.data.remote.service.HomeService
import com.raven.home.di.ServiceFactoryModule.IoDispatcher
import com.raven.home.domain.repository.HomeDataSource
import com.raven.home.domain.mapper.toNewsDomain
import com.raven.home.domain.models.NewsDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    serviceFactory: ServiceFactory,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : HomeDataSource {

    private val service = serviceFactory.makeConnectionApiService(HomeService::class.java)
    override suspend fun getNews(): Flow<Result<NewsDomain>> {
        return flow {
            val response = service.getNews("")
            if (response.isSuccessful) {
                emit(Result.Success(response.body()?.toNewsDomain()))
            } else {
                emit(Result.Error(response.message()))
            }
        }.catch { emit(Result.Error(it.localizedMessage)) }
            .flowOn(dispatcher)
    }
}
