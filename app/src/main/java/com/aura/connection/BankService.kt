package com.aura.connection


import CredentialsResult
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BankService {
    @POST("/login")
    suspend fun getLogin(
        @Body credentials: Credentials
    ): Response<CredentialsResult>


    @GET("/accounts/{id}")

    suspend fun getAccount (
        @Path("id") accountId:Account

    ):Response<Account>




    companion object {

        private const val api_base_url: String = "http://10.0.2.2:8080"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(api_base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}






