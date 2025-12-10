package com.shihab.weather_app_compose.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsStore(context: Context) {
    private val dataStore = context.dataStore

    companion object {
        val IS_CELSIUS = booleanPreferencesKey("is_celsius")
        val NOTIFICATIONS = booleanPreferencesKey("notifications")
    }

    val isCelsius: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_CELSIUS] ?: true
    }

    val notificationsEnabled: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[NOTIFICATIONS] ?: true
    }

    suspend fun saveUnit(isCelsius: Boolean) {
        dataStore.edit { it[IS_CELSIUS] = isCelsius }
    }

    suspend fun saveNotification(isEnabled: Boolean) {
        dataStore.edit { it[NOTIFICATIONS] = isEnabled }
    }
}