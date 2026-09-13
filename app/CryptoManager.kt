package com.secure.vault.core.crypto

import android.util.Base64
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec

class CryptoManager {
    private val transformation = "AES/GCM/NoPadding"
    private val tagLengthBits = 128
    private val ivLengthBytes = 12

    fun encrypt(plaintext: String, secretKey: SecretKey): EncryptedData {
        val cipher = Cipher.getInstance(transformation)
        val iv = bytearray(ivLengthBytes).apply { SecureRandom().nextBytes(this) }
        val parameterSpec = GCMParameterSpec(tagLengthBits, iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec)
        
        val ciphertext = cipher.doFinal(plaintext.toByteArray(Charsets.UTF_8))
        return EncryptedData(
            ciphertext = Base64.encodeToString(ciphertext, Base64.NO_WRAP),
            iv = Base64.encodeToString(iv, Base64.NO_WRAP)
        )
    }

    fun decrypt(ciphertextBase64: String, ivBase64: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(transformation)
        val ciphertext = Base64.decode(ciphertextBase64, Base64.NO_WRAP)
        val iv = Base64.decode(ivBase64, Base64.NO_WRAP)
        val parameterSpec = GCMParameterSpec(tagLengthBits, iv)
        
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec)
        val plaintextBytes = cipher.doFinal(ciphertext)
        return String(plaintextBytes, Charsets.UTF_8)
    }
    
    private fun bytearray(size: Int): ByteArray = ByteArray(size)
}

data class EncryptedData(
    val ciphertext: String,
    val iv: String
)
