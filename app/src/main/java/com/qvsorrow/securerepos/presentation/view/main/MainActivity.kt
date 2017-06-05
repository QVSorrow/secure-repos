package com.qvsorrow.securerepos.presentation.view.main

import android.os.Bundle
import com.qvsorrow.securerepos.R
import com.qvsorrow.securerepos.databinding.ActivityMainBinding
import com.qvsorrow.securerepos.presentation.base.BaseActivity
import com.qvsorrow.securerepos.presentation.view.repos_list.RepoListFragment

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    override val classVM: Class<MainViewModel> = MainViewModel::class.java
    override val layoutId: Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            binding?.container?.let {
                supportFragmentManager.beginTransaction()
                        .add(it.id, RepoListFragment())
                        .commit()
            }
        }
    }

}