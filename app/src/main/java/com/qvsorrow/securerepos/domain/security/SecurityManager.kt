package com.qvsorrow.securerepos.domain.security

interface SecurityManager {
    fun encrypt(data: ByteArray): String
    fun decrypt(data: String): ByteArray
}