package com.firstgroup.secondhand.core.data.repositories.product

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepositoryImpl.Companion.STARTING_PAGE_INDEX
import com.firstgroup.secondhand.core.network.product.ProductRemoteDataSource
import com.firstgroup.secondhand.core.network.product.model.ProductDto
import retrofit2.HttpException
import java.io.IOException

class ProductByCategoryPagingSource(
    private val categoryId: Int,
    private val remoteDataSource: ProductRemoteDataSource
) : PagingSource<Int, ProductDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDto> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val products = remoteDataSource.getProductsByCategory(
                categoryId = categoryId,
                page = position,
                size = params.loadSize,
            )
            val nextKey = if (products.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = products,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, ProductDto>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}