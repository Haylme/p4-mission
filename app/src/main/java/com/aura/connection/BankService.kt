package com.aura.connection

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface BankService {
    @POST("/login")
    suspend fun getLogin(
        @Body loginRequest: LoginRequest
    ): Response<ResponseApi>

    companion object {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

data class LoginRequest(val id: String, val password: String)
data class ResponseApi(val granted: Boolean)
