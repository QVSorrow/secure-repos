package com.qvsorrow.securerepos.presentation.view.repos_list

import com.qvsorrow.securerepos.presentation.base.list.BindingItem
import com.qvsorrow.securerepos.R
import com.qvsorrow.securerepos.domain.model.Repo

class RepoItem(private val repo: Repo) : BindingItem<Repo> {
    override fun getLayoutId(): Int = R.layout.item_github_repo
    override fun getItem(): Repo = repo
}