package com.firstgroup.secondhand.core.data.repositories.product

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.firstgroup.secondhand.core.data.repositories.product.ProductRepositoryImpl.Companion.STARTING_PAGE_INDEX
import com.firstgroup.secondhand.core.database.product.ProductLocalDataSource
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import com.firstgroup.secondhand.core.database.product.entity.RemoteKeys
import com.firstgroup.secondhand.core.network.product.ProductRemoteDataSource
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class ProductRemoteMediator(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
) : RemoteMediator<Int, ProductEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ProductEntity>
    ): MediatorResult {
        val page = when (loadType) {
            REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        return try {
            val products = remoteDataSource.getProductsAsBuyer(
                page = page,
                size = state.config.pageSize,
            ).map { it.mapToEntityModel() }

            val endOfPaginationReached = products.isEmpty()

            localDataSource.cacheProductTransaction {
                // clear all tables in the database
                if (loadType == REFRESH) {
                    localDataSource.clearRemoteKeys()
                    localDataSource.deleteCachedProducts()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = products.map {
                    RemoteKeys(productId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                localDataSource.insertRemoteKeys(keys)
                localDataSource.cacheAllProducts(products)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            Log.d("PagingException", "ioException: $exception")
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            Log.d("PagingException", "httpException: $exception")
            MediatorResult.Error(exception)
        } catch (exception: Exception) {
            Log.d("PagingException", "genericException: $exception")
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ProductEntity>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data
            ?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                localDataSource.getRemoteKeysId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ProductEntity>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data
            ?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                localDataSource.getRemoteKeysId(repo.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ProductEntity>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                localDataSource.getRemoteKeysId(repoId)
            }
        }
    }
}