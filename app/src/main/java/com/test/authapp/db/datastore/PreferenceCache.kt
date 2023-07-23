package com.test.authapp.db.datastore

import kotlinx.coroutines.flow.Flow

interface PreferenceCache {
    suspend fun getBoolean(key: String, default: Boolean = false): Flow<Boolean>
    suspend fun putBoolean(key: String, value: Boolean)
}