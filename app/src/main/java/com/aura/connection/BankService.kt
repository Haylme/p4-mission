package com.aura.connection

import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Path

interface BankService {
    @POST("/login/{id}{password}")
    fun getLogin(@Path("id,password") id: String?, password: String?): Call<Credentials?>?

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.84")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    }
}
