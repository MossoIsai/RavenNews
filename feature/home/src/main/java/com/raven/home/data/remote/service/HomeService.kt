package com.raven.home.data.remote.service

import com.raven.home.data.entities.ResponseNewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HomeService {

    @GET("emailed/7.json?{api-key}")
    suspend fun getNews(@Path("api-key") apiKey: String): Response<ResponseNewsData>
}
