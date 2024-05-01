package com.raven.home.data.source.remote.service

import com.raven.home.data.entities.ResponseNewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("emailed/7.json?")
    suspend fun getNews(@Query("api-key") apiKey: String): Response<ResponseNewsData>
}
