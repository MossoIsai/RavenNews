package com.raven.home.data.repository

import android.util.Log
import com.raven.core.BuildConfig
import com.raven.home.data.Result
import com.raven.home.data.source.local.NewsDao
import com.raven.home.data.source.remote.ServiceFactory
import com.raven.home.data.source.remote.service.HomeService
import com.raven.home.di.ServiceFactoryModule.IoDispatcher
import com.raven.home.domain.mapper.toDomain
import com.raven.home.domain.repository.HomeDataSource
import com.raven.home.domain.mapper.toNewsDomain
import com.raven.home.domain.mapper.toNewsEntity
import com.raven.home.domain.models.ItemNews
import com.raven.home.domain.models.NewsDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(
    serviceFactory: ServiceFactory,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val newsDao: NewsDao
) : HomeDataSource {

    private val service = serviceFactory.makeConnectionApiService(HomeService::class.java)
    override suspend fun getNews(isNetworkAvailable: Boolean): Flow<Result<NewsDomain>> {
        return flow {
            if (isNetworkAvailable) {
                val response = service.getNews(BuildConfig.MY_API_KEY)
                if (response.isSuccessful) {
                    response.body()?.toNewsDomain()?.items?.forEach { item ->
                        newsDao.insertNews(item.toNewsEntity())
                    }
                    emit(Result.Success(response.body()?.toNewsDomain()))
                } else {
                    emit(Result.Error(response.message()))
                }
            } else {
                val arrayx: MutableList<ItemNews> = arrayListOf()
                newsDao.getNews().forEach { newsEntity ->
                    arrayx.add(newsEntity.toDomain())
                }
                emit(Result.Success(NewsDomain(arrayx)))
            }
        }.catch { emit(Result.Error(it.localizedMessage)) }
            .flowOn(dispatcher)
    }
}
