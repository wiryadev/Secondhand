package com.firstgroup.secondhand.core.data.repositories.auth

import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.network.auth.AuthRemoteDataSource
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.core.network.auth.model.LoginDto
import com.firstgroup.secondhand.core.preference.AuthPreferenceDataSource
import com.firstgroup.secondhand.utils.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var remoteDataSource: AuthRemoteDataSource
    private lateinit var preferenceDataSource: AuthPreferenceDataSource
    private lateinit var repository: AuthRepository

    @Before
    fun setUp() {
        remoteDataSource = mock()
        preferenceDataSource = mock()
        repository = AuthRepositoryImpl(remoteDataSource, preferenceDataSource)
    }

    @Test
    fun login_whenAllInputValid_thenReturnAuthenticationModel() = runTest {
        val response = LoginDto(
            accessToken = "token",
            email = "email",
            name = "name",
        )
        val expected = Authentication(
            token = "token",
            email = "email",
            name = "name",
        )
        val loginRequest = LoginRequest(
            email = "email",
            password = "password"
        )

        whenever(remoteDataSource.login(loginRequest))
            .thenReturn(response)

        val actual = repository.login(loginRequest)

        verify(remoteDataSource).login(loginRequest)
        assertEquals(expected, actual)
    }
}