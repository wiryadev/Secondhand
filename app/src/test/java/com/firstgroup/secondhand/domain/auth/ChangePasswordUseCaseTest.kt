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
class ChangePasswordUseCaseTest {
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var repository: AuthRepository
    private lateinit var changePasswordUseCase: ChangePasswordUseCase

    @Before
    fun setUp() {
        repository = mock()
        changePasswordUseCase = ChangePasswordUseCase(repository, dispatcherRule.dispatcher)
    }

    @Test
    fun changePassword_whenInputValid_thenReturnSuccessWithData() = runTest {
        val response = AuthDummyDataProvider.provideValidChangePasswordResponse()

        val param = AuthDummyDataProvider.provideValidChangePasswordParam()

        val request = AuthDummyDataProvider.provideValidChangePasswordRequest()

        whenever(repository.changePassword(request))
            .thenReturn(response)

        val expected = Result.Success(data = response)
        val actual = changePasswordUseCase(param)

        verify(repository).changePassword(request)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(expected, actual)
    }

}