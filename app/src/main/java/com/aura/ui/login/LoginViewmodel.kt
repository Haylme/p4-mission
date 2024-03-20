import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.connection.BankCall
import com.aura.model.CredentialsResult
import com.aura.model.SimpleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel : ViewModel() {

    val isEnabled =
        { id: String, password: String -> id.isNotEmpty() && password.isNotEmpty() }



    private val _loginEnabled = MutableStateFlow(SimpleResponse.initial<Boolean>())
    val loginEnabled: StateFlow<SimpleResponse<Boolean>> = _loginEnabled.asStateFlow()


    private val _toastEvent = MutableStateFlow<String?>(null)
    val toastEvent: StateFlow<String?> = _toastEvent.asStateFlow()


    fun checkLoginCredentials(id: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Attempt to login using the provided credentials
                val responseApi: CredentialsResult = BankCall.fetchLogin(id, password)
                if (responseApi.granted) {
                    _loginEnabled.value = SimpleResponse.success(true)
                } else {
                    _loginEnabled.value = SimpleResponse.failure(Exception("Invalid id or password"))
                    _toastEvent.value = "Invalid id or password"
                }
            } catch (e: HttpException) {
                // Handle HTTP errors with specific messages based on the status code
                _loginEnabled.value = SimpleResponse.failure(e)
                _toastEvent.value = when (e.code()) {
                    401 -> "Unauthorized access, check your credentials"
                    403 -> "Forbidden access, you do not have permission"
                    500 -> "Server error, please try again later"
                    else -> "Server error: ${e.code()}"
                }
            } catch (e: IOException) {
                // Handle network connectivity errors
                _loginEnabled.value = SimpleResponse.failure(e)
                _toastEvent.value = "No network connection"
            } catch (e: Exception) {
                // Handle other exceptions
                _loginEnabled.value = SimpleResponse.failure(e)
                _toastEvent.value = e.message ?: "An unexpected error occurred"
            }
        }
    }

    fun resetLoginState() {
        _loginEnabled.value = SimpleResponse.initial()
    }

    fun resetToastEvent() {
        _toastEvent.value = null
    }


}



