package com.qvsorrow.securerepos.data.database.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Repo")
class EncrRepo(val data: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Integer? = null
}