package com.aura.ui.home

import SimpleResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.connection.Account
import com.aura.connection.BankCall
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewmodel : ViewModel() {

    private val _errorsCollect = MutableStateFlow<String?>(null)
    val errorsCollect: StateFlow<String?> = _errorsCollect.asStateFlow()


    private val _accountDetails = MutableStateFlow(SimpleResponse.initial<Account>())
    val accountDetails: StateFlow<SimpleResponse<Account>> = _accountDetails.asStateFlow()

    fun fetchAccountDetails(id: String) {
        viewModelScope.launch {
            try {
                val account: Account = BankCall.fetchAccount(id)
                _accountDetails.value = SimpleResponse.success(account)
            } catch (e: HttpException) {
                val errorMessage = when (e.code()) {
                    401 -> "Unauthorized access, check your credentials"
                    403 -> "Forbidden access, you do not have permission"
                    500 -> "Server error, please try again later"
                    else -> "Server error: ${e.code()}"
                }
                _errorsCollect.value = errorMessage // Collect the error message
                _accountDetails.value = SimpleResponse.failure(Exception(errorMessage)) // Update account details with failure
            } catch (e: IOException) {
                val errorMessage = "No network connection"
                _errorsCollect.value = errorMessage // Collect the error message
                _accountDetails.value = SimpleResponse.failure(Exception(errorMessage)) // Update account details with failure
            } catch (e: Exception) {
                val errorMessage = e.message ?: "An unknown error occurred"
                _errorsCollect.value = errorMessage // Collect the error message
                _accountDetails.value = SimpleResponse.failure(Exception(errorMessage)) // Update account details with failure
            }
        }
    }

    fun resetToast (){
        _errorsCollect.value = null

    }

    fun resetAccountValue (){

        _accountDetails.value = SimpleResponse.initial()
    }


}