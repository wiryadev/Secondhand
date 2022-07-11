package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.utils.AuthDummyDataProvider
import com.firstgroup.secondhand.utils.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var repository: AuthRepository
    private lateinit var setTokenUseCase: SetTokenUseCase
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        repository = mock()
        setTokenUseCase = mock()
        loginUseCase = LoginUseCase(repository, setTokenUseCase, dispatcherRule.dispatcher)
    }

    @Test
    fun login_whenInputValid_thenReturnSuccessWithData() = runTest {
        val authentication = AuthDummyDataProvider.provideAuthentication()

        val loginParam = AuthDummyDataProvider.provideValidLoginParam()

        val loginRequest = AuthDummyDataProvider.provideValidLoginRequest()

        whenever(repository.login(loginRequest))
            .thenReturn(authentication)

        val expected = Result.Success(data = authentication)
        val actual = loginUseCase(loginParam)

        verify(repository).login(loginRequest)
        verify(setTokenUseCase).invoke(authentication.token)
        assertNotNull(actual)
        assertTrue(actual is Result.Success)
        assertEquals(expected, actual)
    }

    @Test
    fun login_whenInputInvalid_thenReturnError() = runTest {
        val loginParam = AuthDummyDataProvider.provideInvalidLoginParam()

        val loginRequest = AuthDummyDataProvider.provideInvalidLoginRequest()

        val exception = RuntimeException("HTTP Error")
        whenever(repository.login(loginRequest))
            .thenThrow(exception)

        val expected = Result.Error(exception = exception)
        val actual = loginUseCase(loginParam)

        verify(repository).login(loginRequest)
        verify(setTokenUseCase, never()).invoke("")
        assertNotNull(actual)
        assertTrue(actual is Result.Error)
        assertEquals(expected.toString(), actual.toString())
    }

}