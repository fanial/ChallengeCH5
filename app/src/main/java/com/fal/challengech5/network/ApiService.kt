package com.fal.challengech5.network

import com.fal.challengech5.model.ResponseDataTaskItem
import com.fal.challengech5.model.ResponseDataUserItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    //Task
    @GET("user/{id}/task")
    fun getTask(
        @Path("id") userId: String

    ) : Call<List<ResponseDataTaskItem>>

    @PUT("user/{id}/task/{id}")
    fun putData(
        @Path("id") userId: String,
        @Path("id") idTask : String,
        @Body request : ResponseDataTaskItem
    ) : Call<List<ResponseDataTaskItem>>

    @DELETE("user/{id}/task/{id}")
    fun delData(
        @Path("id") userId : String,
        @Path("id") idTask : String
    ) : Call<String>

    //User
    @GET("user")
    fun getUser() : Call<List<ResponseDataUserItem>>

    @POST("user")
    fun postUser(@Body user : ResponseDataUserItem) : Call<ResponseDataUserItem>
}