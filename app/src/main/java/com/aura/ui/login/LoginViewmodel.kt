import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.connection.BankCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val isEnabled =
        { a: String, b: String -> a.isNotEmpty() && b.isNotEmpty() }


    private val _loginEnabled = MutableStateFlow(SimpleResponse.initial<Boolean>())
    val loginEnabled: StateFlow<SimpleResponse<Boolean>> = _loginEnabled.asStateFlow()


    private val _toastEvent = MutableStateFlow<String?>(null)
    val toastEvent: StateFlow<String?> = _toastEvent.asStateFlow()

    fun checkLoginCredentials(id: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // CredentialsResult object from the fetchLogin call
                val responseApi: CredentialsResult = BankCall.fetchLogin(id, password)
               if(responseApi.granted) {

                   _loginEnabled.value = SimpleResponse.success(true)
               }else {
                   _loginEnabled.value =SimpleResponse.failure(Exception("Invalid id or password"))
                   _toastEvent.value = "Invalid id or password"
               }

            } catch (e: Exception) {

                _loginEnabled.value = SimpleResponse.failure(e)

                _toastEvent.value = e.message

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



