package com.qvsorrow.securerepos.presentation.base

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qvsorrow.securerepos.BR
import com.qvsorrow.securerepos.di.Injectable
import com.qvsorrow.securerepos.presentation.di.ViewModelFactory
import javax.inject.Inject


abstract class BaseFragment<VM : ViewModel, Binding : ViewDataBinding> : LifecycleFragment(), Injectable {

    val viewModel: VM by lazy { vmInitializer() }
    var binding: Binding? = null
    abstract val classVM: Class<VM>
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding?.setVariable(BR.viewModel, viewModel)
        return binding?.root
    }

    private fun vmInitializer(): VM = ViewModelProviders.of(this,
            ViewModelFactory(activity.application)).get(classVM)

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }



}