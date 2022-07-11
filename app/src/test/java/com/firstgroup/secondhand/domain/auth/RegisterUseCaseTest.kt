package com.firstgroup.secondhand.domain.auth

import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.data.repositories.auth.AuthRepository
import com.firstgroup.secondhand.utils.AuthDummyDataProvider
import com.firstgroup.secondhand.utils.TestDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class RegisterUseCaseTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var repository: AuthRepository
    private lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setUp() {
        repository = mock()
        registerUseCase = RegisterUseCase(repository, dispatcherRule.dispatcher)
    }

    @Test
    fun register_whenInputValid_thenReturnSuccessWithData() = runTest {
        val user = AuthDummyDataProvider.provideValidUser()

        val param = AuthDummyDataProvider.provideValidRegisterParam()

        val request = AuthDummyDataProvider.provideValidRegisterRequest()

        whenever(repository.register(request))
            .thenReturn(user)

        val expected = Result.Success(data = user)
        val actual = registerUseCase(param)

        verify(repository).register(request)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(expected, actual)
    }

}