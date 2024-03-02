package com.aura.connection

import CredentialsResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
object BankCall {
    suspend fun fetchLogin(id: String?, password: String?): CredentialsResult {
        val service: BankService = BankService.retrofit.create(BankService::class.java)
        return withContext(Dispatchers.IO) {
            if (id != null && password != null) {
                val loginRequest = LoginRequest(id, password)
                try {
                    val response: Response<CredentialsResult> = service.getLogin(loginRequest)
                    if (response.isSuccessful) {
                        response.body() ?: CredentialsResult(granted = false)
                    } else {
                        CredentialsResult(granted = false)
                    }
                } catch (e: HttpException) {
                    CredentialsResult(granted = false)
                } catch (e: Throwable) {
                    CredentialsResult(granted = false)
                }
            } else {
                CredentialsResult(granted = false)
            }
        }
    }
}

