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

            if (id.isNullOrEmpty() || password.isNullOrEmpty()) {
                return@withContext CredentialsResult(granted = false)
            }
            try {
                val loginRequest = Credentials(id, password)
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
        }
    }
   /** suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): SimpleResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiCall()
                if (response.isSuccessful) {
                    SimpleResponse.success(response)
                } else {
                    SimpleResponse.failure(HttpException(response))
                }
            } catch (e: Exception) {
                SimpleResponse.failure(e)
            }
        }
    }**/


}

