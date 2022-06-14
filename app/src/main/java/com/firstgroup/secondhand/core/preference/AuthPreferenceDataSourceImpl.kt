package com.firstgroup.secondhand.core.preference

import com.firstgroup.secondhand.core.preference.datastore.AuthPreference
import com.firstgroup.secondhand.core.preference.model.AuthSessionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthPreferenceDataSourceImpl @Inject constructor(
    private val authPreference: AuthPreference
) : AuthPreferenceDataSource {

    override fun getUserSession(): Flow<AuthSessionModel> {
        return authPreference.getUserSession()
    }

    override suspend fun saveUserSession(user: AuthSessionModel) {
        authPreference.saveUserSession(user = user)
    }

    override suspend fun deleteUserSession() {
        authPreference.deleteUserSession()
    }
}