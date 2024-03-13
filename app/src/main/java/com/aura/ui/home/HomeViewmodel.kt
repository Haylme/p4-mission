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

class HomeViewmodel: ViewModel() {


    private val _accountDetails = MutableStateFlow<Account?>(null)
    val accountDetails: StateFlow<Account?> = _accountDetails.asStateFlow()

    fun fetchAccountDetails(id: String) {
        viewModelScope.launch {
            try {
                val account: Account? = BankCall.fetchAccount(id) // Use actual parameters as needed
                _accountDetails.value = account
            } catch (e: HttpException) {
                // Handle HTTP errors
            } catch (e: IOException) {
                // Handle network connectivity errors
            } catch (e: Exception) {
                // Handle other exceptions
            }
        }
    }


}