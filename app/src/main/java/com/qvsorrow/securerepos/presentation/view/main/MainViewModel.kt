package com.qvsorrow.securerepos.presentation.view.main

import com.qvsorrow.securerepos.presentation.base.BaseViewModel
import com.qvsorrow.securerepos.presentation.di.ViewModelSubcomponent

class MainViewModel : BaseViewModel() {
    override fun inject(viewModelSubcomponent: ViewModelSubcomponent) {
        viewModelSubcomponent.inject(this)
    }


}
