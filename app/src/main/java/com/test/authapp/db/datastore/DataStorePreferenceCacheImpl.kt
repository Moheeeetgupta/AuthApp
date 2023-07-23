package com.test.authapp.db.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_pref")

class DataStorePreferenceCache(context: Context) : PreferenceCache {
    private val dataSource = context.dataStore

    override suspend fun getBoolean(key: String, default: Boolean): Flow<Boolean> =
        dataSource.data.catch { emit(emptyPreferences()) }
            .map { it[booleanPreferencesKey(key)] ?: default }

    override suspend fun putBoolean(key: String, value: Boolean) {
        dataSource.edit { it[booleanPreferencesKey(key)] = value }
    }

    companion object {
        const val IS_AUTHENTICATED = "IS_AUTHENTICATED"
    }
}