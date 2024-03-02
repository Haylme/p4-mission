import android.util.Log
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





    private val _loginEnabled = MutableStateFlow(false)
    val loginEnabled: StateFlow<Boolean> = _loginEnabled.asStateFlow()



    fun checkLoginCredentials(id: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Directly receive a CredentialsResult object from the fetchLogin call
                val responseApi: CredentialsResult = BankCall.fetchLogin(id, password)
                // Update _loginEnabled with the value of granted from the CredentialsResult
                _loginEnabled.value = responseApi.granted
            } catch (e: Exception) {
                // Handle any exceptions that might occur during the network request
                _loginEnabled.value = false

            }
        }
    }




}



