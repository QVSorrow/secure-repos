package com.qvsorrow.securerepos.presentation

import android.arch.lifecycle.LiveData


class ToastLiveData : LiveData<String>() {

    fun sendMessage(message: String): Unit {
        if (this.hasActiveObservers()) {
            value = message
        }
    }
}

