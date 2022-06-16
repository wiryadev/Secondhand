package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.core.model.Authentication
import com.firstgroup.secondhand.core.network.auth.model.LoginRequest
import com.firstgroup.secondhand.utils.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var repository: AuthRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        repository = mock()
        loginUseCase = LoginUseCase(repository, dispatcherRule.dispatcher)
    }

    @Test
    fun login_whenInputValid_thenReturnSuccessWithData() = runTest {
        val authentication = Authentication(
            token = "token",
            email = "email",
            name = "name",
        )

        val loginRequest = LoginRequest(
            email = "email",
            password = "password"
        )

        whenever(repository.login(loginRequest.email, loginRequest.password))
            .thenReturn(authentication)

        val expected = Result.Success(data = authentication)
        val actual = loginUseCase(loginRequest)

        verify(repository).login(loginRequest.email, loginRequest.password)
        assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(expected, actual)
    }

    @Test
    fun login_whenInputInvalid_thenReturnError() = runTest {
        val exception = RuntimeException("HTTP Error")
        whenever(repository.login("", ""))
            .thenThrow(exception)

        val expected = Result.Error(exception = exception)
        val actual = loginUseCase(LoginRequest("", ""))

        verify(repository).login("", "")
        assertNotNull(actual)
        assertTrue(actual is Result.Error)
        if (actual is Result.Error) {
            assertEquals(expected.exception!!.message, actual.exception!!.message)
        }
    }

}