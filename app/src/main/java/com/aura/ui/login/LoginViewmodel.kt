import android.widget.EditText
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val isEnabled =
        { a: EditText, b: EditText -> a.text.trim().isNotEmpty() && b.text.trim().isNotEmpty() }


//fun isEnabled(a: EditText, b:EditText):Boolean{
    // return a.text.trim().isNotEmpty() && b.text.trim().isNotEmpty()

}


/** private val _identifier = MutableStateFlow("")
val identifier: StateFlow<String> = _identifier.asStateFlow()

private val _password = MutableStateFlow("")
val password: StateFlow<String> = _password.asStateFlow()

private val _loginEnabled = MutableStateFlow(false)
val loginEnabled: StateFlow<Boolean> = _loginEnabled.asStateFlow()

// private val _navigateToHome = MutableSharedFlow<Unit>()
//val navigateToHome: SharedFlow<Unit> = _navigateToHome.asSharedFlow()

init {
viewModelScope.launch {
combine(_identifier, _password) { identifier, password ->
identifier.isNotEmpty() && password.isNotEmpty()
}.collect { isValid ->
_loginEnabled.value = isValid
}
}
}

fun onIdentifierChanged(s: CharSequence) {
_identifier.value = s.toString()
}

fun onPasswordChanged(s: CharSequence) {
_password.value = s.toString()
}

fun onLoginClicked() {
viewModelScope.launch {
_navigateToHome.emit(Unit)
}
}**/


