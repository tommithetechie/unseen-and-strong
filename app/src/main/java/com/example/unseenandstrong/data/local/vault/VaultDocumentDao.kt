package com.example.unseenandstrong.data.local.vault

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface VaultDocumentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(document: VaultDocumentEntity): Long

    @Delete
    suspend fun deleteDocument(document: VaultDocumentEntity): Int

    @Query("SELECT * FROM vault_documents ORDER BY dateAdded DESC")
    fun getAllDocuments(): Flow<List<VaultDocumentEntity>>
}

