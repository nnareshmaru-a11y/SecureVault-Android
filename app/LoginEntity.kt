package com.secure.vault.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "logins")
data class LoginEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val encryptedUsername: String,
    val usernameIv: String,
    val encryptedPassword: String,
    val passwordIv: String,
    val encryptedWebsite: String?,
    val websiteIv: String?,
    val encryptedNotes: String?,
    val notesIv: String?,
    val categoryId: Long,
    val favorite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val lastUsedAt: Long? = null
)
