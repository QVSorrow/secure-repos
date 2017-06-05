package com.qvsorrow.securerepos.data.preferences

import android.content.SharedPreferences
import android.util.Base64

class SharedPreferenceManager(private val sharedPreferences: SharedPreferences) : PreferenceManager {


    companion object {
        private const val KEY_AES = "As%#492Fi29Z"
    }

    override fun getEncryptedKey(): String? =
        sharedPreferences.getString(KEY_AES, null)

    override fun setEncryptedKey(key: String) {
        sharedPreferences.edit().putString(KEY_AES, key).apply()
    }
}