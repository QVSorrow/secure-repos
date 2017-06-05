package com.qvsorrow.securerepos.data.database

import com.qvsorrow.securerepos.data.database.dao.RepoDao

interface DatabaseManager {
    fun repoDao(): RepoDao
}