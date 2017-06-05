package com.qvsorrow.securerepos.presentation.di


import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.qvsorrow.securerepos.App

class ViewModelFactory
constructor(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val t = super.create(modelClass)
        if (t is Injectable) t.inject((application as App).component.viewModelSubcomponent().build())
        return t
    }
}

