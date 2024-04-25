package com.raven.home.domain.repository

import com.raven.home.data.Result
import com.raven.home.domain.models.NewsDomain
import kotlinx.coroutines.flow.Flow

interface HomeDataSource {

    suspend fun getNews(): Flow<Result<NewsDomain>>
}
