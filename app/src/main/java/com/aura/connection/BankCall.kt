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


    suspend fun fetchAccount(id: String, main: Boolean, balance: Double): Account? {
        val service: BankService = BankService.retrofit.create(BankService::class.java)
        return withContext(Dispatchers.IO) {
            try {
                val accountLogin = Account(id, main, balance)
                val response: Response<Account> = service.getAccount(accountLogin)
                if (response.isSuccessful) {
                    response.body()
                } else {
                    null // Handle error as needed
                }
            } catch (e: HttpException) {
                null // Handle error as needed
            } catch (e: Throwable) {
                null // Handle error as needed
            }
        }
    }


}

