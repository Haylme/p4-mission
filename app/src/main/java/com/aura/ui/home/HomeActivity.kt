package com.aura.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.aura.R
import com.aura.databinding.ActivityHomeBinding
import com.aura.ui.login.LoginActivity
import com.aura.ui.transfer.TransferActivity
import kotlinx.coroutines.launch

/**
 * The home activity for the app.
 */
class HomeActivity : AppCompatActivity() {

    private val viewmodel: HomeViewmodel by viewModels()

    /**
     * The binding for the home layout.
     */
    private lateinit var binding: ActivityHomeBinding

    /**
     * A callback for the result of starting the TransferActivity.
     */
    private val startTransferActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            //TODO
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val balance = binding.balance
        val transfer = binding.transfer

        

        transfer.setOnClickListener {
            startTransferActivityForResult.launch(
                Intent(
                    this@HomeActivity,
                    TransferActivity::class.java
                )
            )
        }


        // Assuming you have passed the user ID from LoginActivity to HomeActivity via intent
        val userId = intent.getStringExtra("USER_ID_KEY") ?: return

        // Fetch account details using the user ID
        viewmodel.fetchAccountDetails(userId)

        // Observe the account details and update the UI
        lifecycleScope.launch {
            viewmodel.accountDetails.collect { account ->
                account?.let {
                    balance.text = "${it.balance}€"
                } ?: run {
                    // Handle the case where account is null (e.g., show error or default message)
                    binding.balance.text = "Balance not available"
                }


            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.disconnect -> {
                startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}


