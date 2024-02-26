package com.aura.connection

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

object BankCall {

    suspend fun fetchLogin(id: String?, password: String?): ResponseApi {
        val service: BankService = BankService.retrofit.create(BankService::class.java)
        return try {
            withContext(Dispatchers.IO) {

                if (id != null && password != null) {

                    val loginRequest = LoginRequest(id, password)

                    val response: Response<ResponseApi> = service.getLogin(loginRequest)

                    response.body() ?: ResponseApi(granted = false)
                } else {

                    ResponseApi(granted = false)
                }
            }
        } catch (e: HttpException) {

            ResponseApi(granted = false)
        } catch (e: Throwable) {

            ResponseApi(granted = false)
        }
    }
}
