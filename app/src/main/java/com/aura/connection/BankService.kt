package com.aura.connection


import CredentialsResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface BankService {
    @POST("/login")
    suspend fun getLogin(
        @Body loginRequest: LoginRequest
    ): Response<CredentialsResult>

    companion object {

        private const val api_base_url: String = "http://10.0.2.2:8080"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(api_base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

data class LoginRequest(val id: String, val password: String)



//http://127.0.0.1:8080  swagger ui endpoint

//http://10.0.2.2:8080  local server url


