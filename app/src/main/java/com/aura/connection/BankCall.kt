package com.aura.connection


import ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

object BankCall {



    suspend fun fetchLogin(id: String?, password: String?): List<Pojo>? {
        val service: BankService = BankService.retrofit.create(BankService::class.java)
        return try {
            withContext(Dispatchers.IO) {
                val response: Response<List<Pojo>> = id?.let {
                    if (password != null) {
                        service.getLogin(it, password).await()
                    } else {
                        return@withContext ResponseApi(granted = false)
                    }
                } ?: return@withContext ResponseApi(granted = false)

                if (response.isSuccessful && response.body() != null) {
                    ResponseApi(granted = true)
                } else {
                    ResponseApi(granted = false)
                }
            }
        } catch (e: HttpException) {
            ResponseApi(granted = false)
            null
        } catch (e: Throwable) {
            ResponseApi(granted = false)

        }
    }
}
