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
class UpdateUserUseCaseTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var repository: AuthRepository
    private lateinit var updateUserUseCase: UpdateUserUseCase

    @Before
    fun setUp() {
        repository = mock()
        updateUserUseCase = UpdateUserUseCase(repository, dispatcherRule.dispatcher)
    }

    @Test
    fun updateUser_whenInputValid_thenReturnSuccessWithData() = runTest {
        val user = AuthDummyDataProvider.provideValidUser()

        val param = AuthDummyDataProvider.provideValidUpdateParam()

        val request = AuthDummyDataProvider.provideValidUpdateRequest()

        whenever(repository.updateUser(request))
            .thenReturn(user)

        val expected = Result.Success(data = user)
        val actual = updateUserUseCase(param)

        verify(repository).updateUser(request)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(expected, actual)
    }

}