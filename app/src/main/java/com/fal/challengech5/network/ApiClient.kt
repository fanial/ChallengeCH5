package com.fal.challengech5.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    const val BASE_URL = "https://633c326174afaef164041083.mockapi.io/todo_fal_api/"

    val instance : ApiService by lazy {
        val service = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        service.create(ApiService::class.java)
    }
}