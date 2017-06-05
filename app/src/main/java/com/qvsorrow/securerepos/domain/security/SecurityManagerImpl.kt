package com.qvsorrow.securerepos.domain.security

import android.annotation.SuppressLint
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.qvsorrow.securerepos.App
import com.qvsorrow.securerepos.data.preferences.PreferenceManager
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.SecureRandom
import java.util.*
import javax.crypto.*
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.security.auth.x500.X500Principal


class SecurityManagerImpl
@Inject constructor(private val application: App, private val preferencesManager: PreferenceManager)
    : SecurityManager {


    override fun encrypt(data: ByteArray): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, key())
        val iv = cipher.parameters.getParameterSpec(IvParameterSpec::class.java).iv
        val cipherText = cipher.doFinal(data)
        val bytes = cipherText + iv
        val b64 = Base64.encodeToString(bytes, Base64.DEFAULT)
        return b64
    }

    override fun decrypt(data: String): ByteArray {
        val encrypted = Base64.decode(data, Base64.DEFAULT)
        val iv = encrypted.takeLast(12).toByteArray()
        val cipherText = encrypted.dropLast(12).toByteArray()
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.DECRYPT_MODE, key(), GCMParameterSpec(128, iv))
        return cipher.doFinal(cipherText)
    }


    private val key: () -> SecretKey = {
        val keystore = loadKeystore()
        if (keystore.containsAlias(alias))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                (keystore.getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey
            } else {
                val encrKey = preferencesManager.getEncryptedKey()
                if (encrKey == null) {
                    generateKey()
                } else {
                    SecretKeySpec(rsaDecrypt(Base64.decode(encrKey, Base64.DEFAULT)), "AES")
                }
            }
        else generateKey()
    }

    @SuppressLint("WrongConstant")
    private fun generateKey(): SecretKey {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
            val keyGenParamSpec = KeyGenParameterSpec.Builder(alias,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                    .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                    .setKeySize(256)
                    .build()
            keyGenerator.init(keyGenParamSpec)
            return keyGenerator.generateKey()
        } else {
            val start = Calendar.getInstance()
            val end = Calendar.getInstance()
            end.add(Calendar.HOUR, 30)
            val spec = KeyPairGeneratorSpec.Builder(application.applicationContext)
                    .setAlias(alias)
                    .setSubject(X500Principal("CN=" + alias))
                    .setSerialNumber(BigInteger.TEN)
                    .setStartDate(start.time)
                    .setEndDate(end.time)
                    .build()
            val kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore")
            kpg.initialize(spec)
            kpg.generateKeyPair()

            val key = ByteArray(32)
            val secureRandom = SecureRandom()
            secureRandom.nextBytes(key)
            val encryptedKey = rsaEncrypt(key)
            val enryptedKeyB64 = Base64.encodeToString(encryptedKey, Base64.DEFAULT)
            preferencesManager.setEncryptedKey(enryptedKeyB64)
            return SecretKeySpec(key, "AES")
        }
    }

    private fun loadKeystore(): KeyStore {
        val keystore = KeyStore.getInstance("AndroidKeyStore")
        keystore.load(null)
        return keystore
    }

    private fun rsaEncrypt(secret: ByteArray): ByteArray {
        val privateKeyEntry = loadKeystore().getEntry(alias, null) as KeyStore.PrivateKeyEntry
        val inputCipher = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL")
        inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.certificate.publicKey)

        val outputStream = ByteArrayOutputStream()
        val cipherOutputStream = CipherOutputStream(outputStream, inputCipher)
        cipherOutputStream.write(secret)
        cipherOutputStream.close()

        val vals = outputStream.toByteArray()
        return vals
    }

    private fun rsaDecrypt(encrypted: ByteArray): ByteArray {
        val output = Cipher.getInstance(RSA_MODE, "AndroidOpenSSL")
        val keyStore = loadKeystore()
        output.init(Cipher.DECRYPT_MODE, (keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry).privateKey)
        val cipherInputStream = CipherInputStream(
                ByteArrayInputStream(encrypted), output)
        val values = cipherInputStream.readBytes().toList()
        val bytes = ByteArray(values.size)
        for (i in bytes.indices) bytes[i] = values[i]
        return bytes
    }


    companion object {
        private val alias = "Secure Repos Alias"
        private const val RSA_MODE = "RSA/ECB/PKCS1Padding"
        private const val AES_MODE = "AES/GCM/NoPadding"
    }


}