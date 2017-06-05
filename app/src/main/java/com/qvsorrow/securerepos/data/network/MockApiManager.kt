package com.qvsorrow.securerepos.data.network

import com.qvsorrow.securerepos.domain.model.Owner
import com.qvsorrow.securerepos.domain.model.Repo
import io.reactivex.Single

class MockApiManager : ApiManager {
    override fun getReposByUser(user: String): Single<List<Repo>> {
        val repo1 = Repo(1, "First", "My first repository!", "https://github.com/QVSorrow/progfun-wiki", Owner("QVSorrow"))
        val repo2 = Repo(2, "Second", "My second repository!", "https://github.com/QVSorrow/progfun-wiki", Owner("QVSorrow"))
        return Single.just(listOf(repo1, repo2))
    }
}