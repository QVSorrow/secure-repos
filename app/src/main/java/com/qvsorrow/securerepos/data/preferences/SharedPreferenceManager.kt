package com.qvsorrow.securerepos.data.preferences

import android.content.SharedPreferences
import android.util.Base64

class SharedPreferenceManager(private val sharedPreferences: SharedPreferences) : PreferenceManager {

    companion object {
        private val keyIV = "As%#492Fi29Z"
    }

    override fun setIV(byteArray: ByteArray) {
        sharedPreferences.edit().putString(keyIV, Base64.encodeToString(byteArray, Base64.DEFAULT)).commit()
    }

    override fun getIV(): ByteArray = Base64.decode(sharedPreferences.getString(keyIV, ""), Base64.DEFAULT)


}