package com.qvsorrow.securerepos.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.qvsorrow.securerepos.data.database.entity.EncrRepo
import io.reactivex.Flowable

@Dao
interface RepoDao {

    @Query("SELECT * FROM Repo")
    fun getAll() : Flowable<List<EncrRepo>>

    @Insert
    fun insert(repo: EncrRepo)

    @Insert
    fun insertAll(vararg repo: EncrRepo)

    @Delete
    fun delete(repo: EncrRepo)

    @Query("DELETE FROM Repo")
    fun deleteAll()
}