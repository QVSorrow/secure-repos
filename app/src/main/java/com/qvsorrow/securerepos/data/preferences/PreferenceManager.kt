package com.qvsorrow.securerepos.data.preferences

interface PreferenceManager {


    fun setIV(byteArray: ByteArray): Unit
    fun getIV(): ByteArray

}