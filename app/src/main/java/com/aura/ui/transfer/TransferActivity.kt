package com.aura.ui.transfer

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aura.databinding.ActivityTransferBinding

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
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(s: Editable?) {
                val recipientText = recipient.text.trim().toString()
                val amountText = amount.text.trim().toString()
                val amountValue =
                    amountText.toIntOrNull() ?: 0 // Convert to Int or default to 0 if not valid

                transfer.isEnabled = viewModelTransfer.checkField(recipientText, amountValue)


            }


        }

        recipient.addTextChangedListener(textWatcher)
        amount.addTextChangedListener(textWatcher)


        transfer.setOnClickListener {


            


        }


    }

}
