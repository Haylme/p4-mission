package com.aura.ui.transfer

import androidx.lifecycle.ViewModel

class TransferViewmodel: ViewModel() {


    val checkField = {recipient:String,amount:Int -> recipient.isNotEmpty() && amount >0}

}