package com.qvsorrow.securerepos.presentation.view.repos_list

import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.qvsorrow.securerepos.presentation.base.list.BindingItem
import com.qvsorrow.securerepos.presentation.base.list.RecyclerAdapter
import com.qvsorrow.securerepos.R
import com.qvsorrow.securerepos.databinding.FragmentRepoListBinding
import com.qvsorrow.securerepos.domain.model.Repo
import com.qvsorrow.securerepos.presentation.base.BaseFragment

class RepoListFragment : BaseFragment<RepoListViewModel, FragmentRepoListBinding>() {

    override val layoutId: Int = R.layout.fragment_repo_list
    override val classVM = RepoListViewModel::class.java


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getToastLiveData().observe(this,
                Observer { Toast.makeText(context, it, LENGTH_SHORT).show() })
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.adapter = RecyclerAdapter<BindingItem<Repo>>()
        binding?.recyclerView?.addItemDecoration(
                DividerItemDecoration(binding?.recyclerView?.context, 1))
        binding?.swipe?.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN)
    }
}