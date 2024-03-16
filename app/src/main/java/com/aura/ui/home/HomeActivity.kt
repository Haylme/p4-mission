package com.aura.ui.home

import SimpleResponse
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
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

        val retry = binding.retry



        transfer.setOnClickListener {
            startTransferActivityForResult.launch(
                Intent(
                    this@HomeActivity,
                    TransferActivity::class.java
                )
            )
        }

        retry.visibility = View.GONE

        val userId = intent.getStringExtra("USER_ID_KEY") ?: return

        // Fetch account details using the user ID
        viewmodel.fetchAccountDetails(userId)


        lifecycleScope.launch {
            viewmodel.accountDetails.collect { account ->

                when (account.status) {

                    is SimpleResponse.Status.Success -> {

                        account.let {
                            balance.text = "${account.data?.balance}€"
                        }

                    }

                    is SimpleResponse.Status.Failure -> {

                        lifecycleScope.launch {

                            viewmodel.errorsCollect.collect { message ->
                                message?.let {
                                    Toast.makeText(this@HomeActivity, it, Toast.LENGTH_SHORT)
                                        .show()
                                    // Reset the event after handling
                                    viewmodel.resetToast()
                                }
                            }
                        }


                    }

                    else -> {}


                }

                    ?: run {
                        // Handle the case where account is null
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


