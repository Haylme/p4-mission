package com.aura.connection

import CredentialsResult
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


object BankCall {
    suspend fun fetchLogin(id: String?, password: String?): CredentialsResult {
        val service: BankService = BankService.retrofit.create(BankService::class.java)
        return withContext(Dispatchers.IO) {
            if (id.isNullOrEmpty() || password.isNullOrEmpty()) {
                throw IllegalArgumentException("ID or password must not be null or empty")
            }
            val loginRequest = Credentials(id, password)
            val response: Response<CredentialsResult> = service.getLogin(loginRequest)
            if (response.isSuccessful) {
                response.body() ?: throw IllegalStateException("Received null response body")
            } else {
                throw HttpException(response)
            }
        }
    }


    suspend fun fetchAccount(id: String): Account? {
        val service: BankService = BankService.retrofit.create(BankService::class.java)
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<List<Account>> = service.getAccount(id)
                if (response.isSuccessful) {
                    val accounts = response.body()
                    if (!accounts.isNullOrEmpty()) {
                        accounts[0] // Return the first account in the list or handle as needed
                    } else {
                        throw IllegalStateException("Received empty account list")
                    }
                } else {
                    throw HttpException(response)
                }
            } catch (e: HttpException) {
                Log.e("BankCall", "HttpException: ${e.response()?.errorBody()?.string()}", e)
                throw e
            } catch (e: IOException) {
                Log.e("BankCall", "IOException: No network connection", e)
                throw e
            } catch (e: Throwable) {
                Log.e("BankCall", "Unknown error occurred", e)
                throw e
            }
        }
    }


}

