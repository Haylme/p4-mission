package com.aura.ui.home

import android.widget.TextView
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


    private val _accountDetails = MutableStateFlow<Account?>(null)
    val accountDetails: StateFlow<Account?> = _accountDetails.asStateFlow()

    fun fetchAccountDetails(id: String) {
        viewModelScope.launch {
            try {
                val account: Account = BankCall.fetchAccount(id)

                _accountDetails.value = account
            } catch (e: HttpException) {
                _errorsCollect.value = when (e.code()) {

                    401 -> "Unauthorized access, check your credentials"
                    403 -> "Forbidden access, you do not have permission"
                    500 -> "Server error, please try again later"
                    else -> "Server error: ${e.code()}"


                }
            } catch (e: IOException) {
               _errorsCollect.value = "No network connection"
            } catch (e: Exception) {

                _errorsCollect.value = e.message?: "An unexpected error occurred"
            }
        }
    }


}