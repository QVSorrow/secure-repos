package com.qvsorrow.securerepos.data.repository

import com.google.gson.Gson
import com.qvsorrow.securerepos.data.database.DatabaseManager
import com.qvsorrow.securerepos.data.database.entity.EncrRepo
import com.qvsorrow.securerepos.data.network.ApiManager
import com.qvsorrow.securerepos.domain.model.Repo
import com.qvsorrow.securerepos.domain.repository.GithubRepository
import com.qvsorrow.securerepos.domain.security.SecurityManager
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class GithubRepositoryImpl
@Inject constructor(private val api: ApiManager, private val db: DatabaseManager,
                    private val securityManager: SecurityManager, private val gson: Gson)
    : GithubRepository {

    private val user: String = "QVSorrow"

    override fun refreshData(): Completable = saveDataFromApi()

    override fun getAll(): Observable<List<Repo>> = getRepoFromDb()

    private fun getRepoFromDb(): Observable<List<Repo>> =
            db.repoDao().getAll().toObservable().map { it.map(this::decrypt) }

    private fun saveDataFromApi() =
            api.getReposByUser(user)
                    .flatMapCompletable { list -> delete().andThen(insert(list)) }

    private fun delete() = Completable.fromAction { db.repoDao().deleteAll() }

    private fun insert(list: List<Repo>) =
            Completable.fromAction {
                db.repoDao().insertAll(*(list.map(this::encrypt).toTypedArray()))
            }

    private fun encrypt(repo: Repo): EncrRepo {
        val encrypted = securityManager.encrypt(gson.toJson(repo).toByteArray(Charsets.UTF_8))
        return EncrRepo(encrypted)
    }

    private fun decrypt(encrRepo: EncrRepo): Repo {
        val decrypted = securityManager.decrypt(encrRepo.data)
        val json = String(decrypted, Charsets.UTF_8)
        return gson.fromJson(json, Repo::class.java)
    }

}