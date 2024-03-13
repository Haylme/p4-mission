package com.aura.ui.login

import LoginViewModel
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aura.databinding.ActivityLoginBinding
import com.aura.ui.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * The login activity for the app.
 */
class LoginActivity : AppCompatActivity() {


    /**
     * The binding for the login layout.
     */
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val login = binding.login
        val loading = binding.loading

        val identifier = binding.identifier
        val password = binding.password


        // Initially disable the login button
        login.isEnabled = false


        // Define a common TextWatcher for both EditTexts
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }


            override fun afterTextChanged(s: Editable?) {
                // Enable the login button only if both fields are not empty
                login.isEnabled = viewModel.isEnabled(
                    identifier.text.trim().toString(),
                    password.text.trim().toString()
                )
            }
        }

        // Set the TextWatcher to both EditTexts
        identifier.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)


        // Set the click listener for the login button
        login.setOnClickListener {

            viewModel.resetLoginState()

            loading.visibility = View.VISIBLE


            val id = identifier.text.toString()
            val pass = password.text.toString()

            viewModel.checkLoginCredentials(id, pass)

            login.isEnabled = false


        }


        lifecycleScope.launch {
            viewModel.loginEnabled.collect { response ->
                when (response.status) {
                    is SimpleResponse.Status.Success -> {
                        loading.visibility = View.GONE
                        val id = identifier.text.toString()
                        navigateToHome(id)
                    }

                    is SimpleResponse.Status.Failure -> {
                        loading.visibility = View.GONE


                        lifecycleScope.launch {

                            viewModel.toastEvent.collect { message ->
                                message?.let {
                                    Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT)
                                        .show()
                                    // Reset the event after handling
                                    viewModel.resetToastEvent()
                                }


                            }
                        }


                        binding.identifier.setText("")
                        binding.password.setText("")
                        viewModel.resetToastEvent()
                    }

                    else -> {}
                }
                login.isEnabled = response.status !is SimpleResponse.Status.Success
            }

        }

    }


    @RequiresApi(Build.VERSION_CODES.Q)
    private fun navigateToHome(userId:String) {
        val intent = Intent(this@LoginActivity, HomeActivity::class.java).apply {
            putExtra("USER_ID_KEY", userId)
        }
        startActivity(intent)
        finish()

    }
}

