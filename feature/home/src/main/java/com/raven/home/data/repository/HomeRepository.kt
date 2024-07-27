package com.raven.home.data.repository

import com.raven.core.BuildConfig
import com.raven.home.data.Result
import com.raven.home.data.source.local.NewsDao
import com.raven.home.data.source.local.NewsEntity
import com.raven.home.data.source.remote.ServiceFactory
import com.raven.home.data.source.remote.service.HomeService
import com.raven.home.domain.mapper.toDomain
import com.raven.home.domain.repository.HomeDataSource
import com.raven.home.domain.mapper.toNewsDomain
import com.raven.home.domain.mapper.toNewsEntity
import com.raven.home.domain.models.ItemNews
import com.raven.home.domain.models.NewsDomain
import com.raven.home.presentation.extensions.handlerErrorMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    serviceFactory: ServiceFactory,
    private val newsDao: NewsDao
) : HomeDataSource {

    private val service = serviceFactory.makeConnectionApiService(HomeService::class.java)
    override suspend fun getNews(): Flow<Result<NewsDomain>> {
        return flow {
            val response = service.getNews(BuildConfig.MY_API_KEY)
            if (response.isSuccessful) {
                response.body()?.toNewsDomain()?.items?.forEach { item ->
                    newsDao.insertNews(item.toNewsEntity())
                }
                emit(Result.Success(response.body()?.toNewsDomain()))
            } else {
                emit(Result.Error(response.message()))
            }
        }.catch {
            emit(Result.Error(it.handlerErrorMessage()))
            val daoFlow: Flow<List<NewsEntity>> = newsDao.getNews()
            daoFlow.collect { listNewsEntity ->
                val items: MutableList<ItemNews> = arrayListOf()
                if (listNewsEntity.isNotEmpty()) {
                    listNewsEntity.forEach { entity ->
                        items.add(entity.toDomain())
                    }
                    emit(Result.Success(NewsDomain(items)))
                } else {
                    emit(Result.Success(NewsDomain(arrayListOf())))
                }
            }
        }
    }
}
