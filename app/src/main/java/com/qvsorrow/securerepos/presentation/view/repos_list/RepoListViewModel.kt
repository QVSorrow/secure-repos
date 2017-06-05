package com.qvsorrow.securerepos.presentation.view.repos_list

import android.app.Application
import android.arch.lifecycle.LiveData
import android.content.Intent
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.databinding.ObservableList
import android.net.Uri
import android.util.Log
import com.qvsorrow.securerepos.domain.model.Repo
import com.qvsorrow.securerepos.domain.repository.GithubRepository
import com.qvsorrow.securerepos.presentation.ToastLiveData
import com.qvsorrow.securerepos.presentation.base.BaseViewModel
import com.qvsorrow.securerepos.presentation.base.list.BindingItem
import com.qvsorrow.securerepos.presentation.base.list.ItemEventListener
import com.qvsorrow.securerepos.presentation.di.ViewModelSubcomponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoListViewModel : BaseViewModel(), ItemEventListener {

    override fun inject(viewModelComponent: ViewModelSubcomponent) {
        viewModelComponent.inject(this)
        getReposFromGithub()
    }

    @Inject lateinit var application: Application
    @Inject lateinit var githubRepository: GithubRepository

    val isLoading: ObservableBoolean = ObservableBoolean(false)
    val items: ObservableList<BindingItem<Repo>> = ObservableArrayList()

    private val compositeDisposable = CompositeDisposable()
    private val toastLiveData = ToastLiveData()

    fun getToastLiveData(): LiveData<String> = toastLiveData

    fun onRefresh() {
        isLoading.set(true)
        compositeDisposable += githubRepository.refreshData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onError = {
                    isLoading.set(false)
                    it.message?.let { toastLiveData.sendMessage(it) }
                })
    }

    fun getReposFromGithub() {
        isLoading.set(true)
        var isFirst = true
        compositeDisposable += githubRepository.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(onNext = {
                    if(isFirst && it.isEmpty()) {
                        isFirst = false
                        onRefresh()
                    } else {
                        isLoading.set(false)
                        items.clear()
                        items.addAll(it.map { RepoItem(it) })
                    }
                }, onError = {
                    it.printStackTrace()
                    it.message?.let { toastLiveData.sendMessage(it) }
                })
    }

    fun onItemClick(item: Repo) {
        application.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.htmlUrl)))
    }


    override fun onCleared() {
        compositeDisposable.dispose()
    }
}

