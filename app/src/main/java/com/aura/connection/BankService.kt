package com.aura.connection

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Path

interface BankService {
    @POST("/login/{id}/{password}")
    suspend fun getLogin(
        @Path("id") id: String,
        @Path("password") password: String
    ): Response<List<Pojo>>

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
