package com.aura.ui.login

import LoginViewModel
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aura.databinding.ActivityLoginBinding
import com.aura.ui.home.HomeActivity

/**
 * The login activity for the app.
 */
class LoginActivity : AppCompatActivity() {

    /**
     * The binding for the login layout.
     */
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()


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
               login.isEnabled = viewModel.isEnabled(identifier,password)
            }
        }

        // Set the TextWatcher to both EditTexts
        identifier.addTextChangedListener(textWatcher)
        password.addTextChangedListener(textWatcher)

        // Set the click listener for the login button
        login.setOnClickListener {
            loading.visibility = View.VISIBLE

            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)

            finish()
        }
    }

}
