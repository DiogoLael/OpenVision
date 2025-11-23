package com.example.openvision.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.openvision.utils.DaltonismUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "accessibility_preferences")

class DaltonismPreference(private val context: Context) {

    companion object {
        private val DALTONISM_TYPE_KEY = intPreferencesKey("daltonism_type")
    }

    suspend fun saveDaltonismType(type: DaltonismUtil.DaltonismType) {
        context.dataStore.edit { preferences ->
            preferences[DALTONISM_TYPE_KEY] = type.ordinal
        }
    }

    fun getDaltonismType(): Flow<DaltonismUtil.DaltonismType> {
        return context.dataStore.data.map { preferences ->
            val ordinal = preferences[DALTONISM_TYPE_KEY] ?: DaltonismUtil.DaltonismType.NONE.ordinal
            DaltonismUtil.DaltonismType.values().getOrNull(ordinal) ?: DaltonismUtil.DaltonismType.NONE
        }
    }
}