package com.aura.ui.transfer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aura.databinding.ActivityTransferBinding
import com.aura.model.SimpleResponse
import com.aura.ui.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

/**
 * The transfer activity for the app.
 */
class TransferActivity : AppCompatActivity() {


    private val viewModelTransfer: TransferViewmodel by viewModels()

    /**
     * The binding for the transfer layout.
     */
    private lateinit var binding: ActivityTransferBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recipient = binding.recipient
        val amount = binding.amount
        val transfer = binding.transfer
        val loading = binding.loading



        transfer.setOnClickListener {
            loading.visibility = View.VISIBLE

            setResult(Activity.RESULT_OK)
            finish()
        }


        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val recipientText = recipient.text.trim().toString()
                val amountText = amount.text.trim().toString()
                val amountValue =
                    amountText.toIntOrNull() ?: 0

                transfer.isEnabled = viewModelTransfer.checkField(recipientText, amountValue)


            }


        }

        recipient.addTextChangedListener(textWatcher)
        amount.addTextChangedListener(textWatcher)

        val userId = intent.getStringExtra("USER_ID_KEY") ?: return


        transfer.setOnClickListener {

            viewModelTransfer.resetTransferState()

            loading.visibility = View.VISIBLE

            val sendTo = recipient.text.toString()
            val totalText = amount.text.toString()
            val total = totalText.toDoubleOrNull()
            val id = userId

            if (total != null) {
                viewModelTransfer.checkTransfer(id, sendTo, total)
            }

            transfer.isEnabled = false


        }

        lifecycleScope.launch {

            viewModelTransfer.transferCheck.collect { response ->
                when (response.status) {

                    is SimpleResponse.Status.Success -> {
                        val newAmount = amount.text.trim().toString()

                        navigateToHome(newAmount)
                    }

                    is SimpleResponse.Status.Failure -> {

                        loading.visibility = View.GONE

                        transfer.isEnabled = false

                        lifecycleScope.launch {

                            viewModelTransfer.toastEvent.collect { message ->
                                message?.let {
                                    Toast.makeText(this@TransferActivity, it, Toast.LENGTH_SHORT)
                                        .show()

                                    viewModelTransfer.resetToastEvent()

                                }
                            }
                        }
                        binding.amount.setText("")
                        binding.recipient.setText("")

                    }

                }


            }


        }


    }

    private fun navigateToHome(newValue: String) {
        val intent = Intent(this@TransferActivity, HomeActivity::class.java).apply {
            putExtra("account_new_value", newValue)
        }
        startActivity(intent)
        finish()

    }

}
