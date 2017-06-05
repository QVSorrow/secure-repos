package com.qvsorrow.securerepos.data.preferences

interface PreferenceManager {


    fun getEncryptedKey(): String?
    fun setEncryptedKey(key: String)

}