package com.firstgroup.secondhand.domain.order

import com.firstgroup.secondhand.core.common.result.Result
import com.firstgroup.secondhand.core.data.repositories.order.OrderRepository
import com.firstgroup.secondhand.utils.OrderDummyDataProvider
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
class CreateOrderUseCaseTest {

    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var repository: OrderRepository
    private lateinit var createOrderUseCase: CreateOrderUseCase

    @Before
    fun setUp() {
        repository = mock()
        createOrderUseCase = CreateOrderUseCase(repository, dispatcherRule.dispatcher)
    }

    @Test
    fun createOrder_whenInputValid_thenReturnSuccessWithData() = runTest {
        val response = OrderDummyDataProvider.provideValidCreateOrderResponse()

        val param = OrderDummyDataProvider.provideValidCreateOrderParam()

        whenever(repository.createOrder(productId = param.productId, bidPrice = param.bidPrice))
            .thenReturn(response)

        val expected = Result.Success(data = response)
        val actual = createOrderUseCase(param)

        verify(repository).createOrder(productId = param.productId, bidPrice = param.bidPrice)
        Assert.assertNotNull(actual)
        Assert.assertTrue(actual is Result.Success)
        Assert.assertEquals(expected, actual)
    }

}