package com.example.unseenandstrong.data.local.vault

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vault_documents")
data class VaultDocumentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,
    val fileUri: String,
    val dateAdded: Long
)

