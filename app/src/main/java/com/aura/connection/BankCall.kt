package com.aura.connection

import com.aura.model.CredentialsResult
import com.aura.model.Account
import com.aura.model.Credentials
import com.aura.model.TransferResult
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


    suspend fun fetchAccount(id: String): Account {
        val service: BankService = BankService.retrofit.create(BankService::class.java)
        return withContext(Dispatchers.IO) {

            val response: Response<List<Account>> = service.getAccount(id)
            if (response.isSuccessful) {
                val accounts = response.body()
                if (!accounts.isNullOrEmpty()) {
                    accounts[0]
                } else {
                    throw IllegalStateException("Received empty account list")
                }
            } else {
                throw HttpException(response)
            }
        }
    }


    suspend fun fetchTransfer(sender:String , recipient: String, amount: Double): TransferResult {
        val service: BankService = BankService.retrofit.create(BankService::class.java)
        return withContext(Dispatchers.IO) {
            val response: Response<TransferResult> = service.getTransfer(sender,recipient, amount)

            if (response.isSuccessful) {
                // Return the body of the response if it's successful
                response.body() ?: throw HttpException(response)
            } else {
                // Throw an exception if the response is not successful
                throw HttpException(response)
            }
        }
    }



}

