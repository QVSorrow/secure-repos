package com.qvsorrow.securerepos.domain.security

import android.security.keystore.KeyProperties

class SecurityManagerImpl
@javax.inject.Inject constructor(private val preferences: com.qvsorrow.securerepos.data.preferences.PreferenceManager): SecurityManager {


    companion object {
        private val alias = "Secure Repos Alias"
    }



    override fun encrypt(data: ByteArray): String {
        val cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key())
        val iv = cipher.parameters.getParameterSpec(javax.crypto.spec.IvParameterSpec::class.java).iv
        val cipherText = cipher.doFinal(data)
        val bytes = cipherText + iv
        val b64 = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
        return b64
    }

    override fun decrypt(data: String): ByteArray {
        val encrypted = android.util.Base64.decode(data, android.util.Base64.DEFAULT)
        val iv = encrypted.takeLast(12).toByteArray()
        val cipherText = encrypted.dropLast(12).toByteArray()
        val cipher = javax.crypto.Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key(), javax.crypto.spec.GCMParameterSpec(128, iv))
        return cipher.doFinal(cipherText)
    }


    private val key: () -> javax.crypto.SecretKey = {
            val keystore = loadKeystore()
        if (keystore.containsAlias(com.qvsorrow.securerepos.domain.security.SecurityManagerImpl.Companion.alias))
            (keystore.getEntry(com.qvsorrow.securerepos.domain.security.SecurityManagerImpl.Companion.alias, null) as java.security.KeyStore.SecretKeyEntry).secretKey
        else generateKey()
        }

    private fun generateKey(): javax.crypto.SecretKey {
        val keyGenerator = javax.crypto.KeyGenerator.getInstance(android.security.keystore.KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val keyGenParamSpec = android.security.keystore.KeyGenParameterSpec.Builder(alias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setEncryptionPaddings(android.security.keystore.KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setBlockModes(android.security.keystore.KeyProperties.BLOCK_MODE_GCM)
                    .setKeySize(256)
                    .build()
            keyGenerator.init(keyGenParamSpec)
            return keyGenerator.generateKey()
        } else {
            keyGenerator.init(256, java.security.SecureRandom())
            val secretKey = keyGenerator.generateKey()
            val entry = java.security.KeyStore.SecretKeyEntry(secretKey)
            loadKeystore().setEntry(com.qvsorrow.securerepos.domain.security.SecurityManagerImpl.Companion.alias, entry, null)
            return secretKey
        }
    }

    private fun loadKeystore(): java.security.KeyStore {
        val keystore = java.security.KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        return keystore
    }

}