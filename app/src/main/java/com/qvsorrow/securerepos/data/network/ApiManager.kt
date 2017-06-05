package com.qvsorrow.securerepos.data.network

import com.qvsorrow.securerepos.domain.model.Repo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiManager {

    @GET("users/{user}/repos")
    fun getReposByUser(@Path("user") user: String): Single<List<Repo>>

}