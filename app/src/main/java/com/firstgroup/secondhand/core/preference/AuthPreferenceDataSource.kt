package com.firstgroup.secondhand.core.preference

import com.firstgroup.secondhand.core.preference.model.AuthSessionModel
import kotlinx.coroutines.flow.Flow

interface AuthPreferenceDataSource {

    fun getUserSession(): Flow<AuthSessionModel>

    suspend fun saveUserSession(user: AuthSessionModel)

    suspend fun deleteUserSession()

}