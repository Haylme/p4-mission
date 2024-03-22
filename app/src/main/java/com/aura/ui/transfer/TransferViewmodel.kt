package com.aura.ui.transfer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.connection.BankCall
import com.aura.model.SimpleResponse
import com.aura.model.TransferResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class TransferViewmodel : ViewModel() {


    val checkField = { recipient: String, amount: Double -> recipient.isNotEmpty() && amount > 0.0 }

    private val _transferCheck = MutableStateFlow(SimpleResponse.initial<Boolean>())
    val transferCheck: StateFlow<SimpleResponse<Boolean>> = _transferCheck.asStateFlow()

    private val _toastEvent = MutableStateFlow<String?>(null)
    val toastEvent: StateFlow<String?> = _toastEvent.asStateFlow()


    fun checkTransfer(sender:String, recipient: String, amount: Double) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val responseApi: TransferResult = BankCall.fetchTransfer(sender,recipient, amount)
                if (responseApi.result) {
                    _transferCheck.value = SimpleResponse.success(true)
                }else {

                    _transferCheck.value = SimpleResponse.failure(Exception(("Cannot transfer, please retry")))
                }

            } catch (e: HttpException) {
                // Handle HTTP errors with specific messages based on the status code
                _transferCheck.value = SimpleResponse.failure(e)
                _toastEvent.value = when (e.code()) {
                    401 -> "Unauthorized access, check your credentials"
                    403 -> "Forbidden access, you do not have permission"
                    500 -> "Server error, please try again later"
                    else -> "Server error: ${e.code()}"
                }
            } catch (e: IOException) {
                // Handle network connectivity errors
                _transferCheck.value = SimpleResponse.failure(e)
                _toastEvent.value = "No network connection"
            } catch (e: Exception) {
                // Handle other exceptions
                _transferCheck.value = SimpleResponse.failure(e)
                _toastEvent.value = e.message ?: "An unexpected error occurred"
            }
        }


    }

    fun resetTransferState(){
        _transferCheck.value = SimpleResponse.initial()

    }

    fun resetToastEvent() {
        _toastEvent.value = null
    }




}