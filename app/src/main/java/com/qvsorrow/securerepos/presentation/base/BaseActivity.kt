package com.qvsorrow.securerepos.presentation.base

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.qvsorrow.securerepos.BR
import com.qvsorrow.securerepos.presentation.di.ViewModelFactory
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject


abstract class BaseActivity<VM : ViewModel, Binding : ViewDataBinding> : AppCompatActivity(),
        LifecycleRegistryOwner, HasSupportFragmentInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    val viewModel: VM by lazy { vmInitializer() }
    var binding: Binding? = null

    abstract val layoutId: Int
    abstract val classVM: Class<VM>

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            dispatchingAndroidInjector

    private val mRegistry = LifecycleRegistry(this)

    fun BaseActivity() {}

    override fun getLifecycle(): LifecycleRegistry {
        return this.mRegistry
    }

    private fun vmInitializer(): VM = ViewModelProviders.of(this, ViewModelFactory(application)).get(classVM)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<Binding>(this, layoutId)
        binding?.setVariable(BR.viewModel, viewModel)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }


}