package com.firstgroup.secondhand.core.preference.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.firstgroup.secondhand.core.preference.model.AuthSessionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthPreference @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    fun getUserSession(): Flow<AuthSessionModel> = dataStore.data.map { pref ->
        AuthSessionModel(
            name = pref[NAME_KEY] ?: "",
            email = pref[EMAIL_KEY] ?: "",
            token = pref[TOKEN_KEY] ?: "",
        )
    }

    suspend fun saveUserSession(user: AuthSessionModel) {
        dataStore.edit { pref ->
            pref[NAME_KEY] = user.name
            pref[EMAIL_KEY] = user.email
            pref[TOKEN_KEY] = user.token
        }
    }

    suspend fun deleteUserSession() {
        dataStore.edit { pref ->
            pref[NAME_KEY] = ""
            pref[EMAIL_KEY] = ""
            pref[TOKEN_KEY] = ""
        }
    }
}

private val NAME_KEY = stringPreferencesKey("name")
private val EMAIL_KEY = stringPreferencesKey("email")
private val TOKEN_KEY = stringPreferencesKey("token")