import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aura.connection.BankCall
import com.aura.connection.ResponseApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val isEnabled =
        { a: EditText, b: EditText -> a.text.trim().isNotEmpty() && b.text.trim().isNotEmpty() }


    private val idCompare = MutableStateFlow("")
    val id1: StateFlow<String> = idCompare.asStateFlow()

    private val passCompare = MutableStateFlow("")
    val pass1: StateFlow<String> = passCompare.asStateFlow()


    private val _loginEnabled = MutableStateFlow(false)
    val loginEnabled: StateFlow<Boolean> = _loginEnabled.asStateFlow()

    fun checkLoginCredentials(id: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val responseApi: ResponseApi = BankCall.fetchLogin(id, password)

            _loginEnabled.value = responseApi.granted

            idCompare.value = id
            passCompare.value = password
        }
    }
}



