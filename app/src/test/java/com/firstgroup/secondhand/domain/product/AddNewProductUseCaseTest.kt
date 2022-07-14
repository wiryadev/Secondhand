package com.firstgroup.secondhand.domain.product

import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepository
import com.firstgroup.secondhand.utils.ProductDummyDataProvider
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
class AddNewProductUseCaseTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var repository: ProductRepository
    private lateinit var addNewProductUseCase: AddNewProductUseCase

    @Before
    fun setUp() {
        repository = mock()
        addNewProductUseCase = AddNewProductUseCase(repository, dispatcherRule.dispatcher)
    }

    @Test
    fun addNewProduct_whenInputValid_thenReturnSuccessWithData() = runTest {
        val response = ProductDummyDataProvider.provideValidProduct()

        val param = ProductDummyDataProvider.provideValidProductParam()

        val request = ProductDummyDataProvider.provideValidProductRequest()

        whenever(repository.addNewProduct(request))
            .thenReturn(response)

        val expected = Result.Success(data = response)
        val actual = addNewProductUseCase(param)

        verify(repository).addNewProduct(request)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(expected, actual)
    }

}