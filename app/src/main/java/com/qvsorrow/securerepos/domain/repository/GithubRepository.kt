package com.qvsorrow.securerepos.domain.repository

import com.qvsorrow.securerepos.domain.model.Repo
import io.reactivex.Completable
import io.reactivex.Observable

interface GithubRepository {

    fun getAll(): Observable<List<Repo>>

    fun refreshData(): Completable
}