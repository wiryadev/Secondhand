package com.firstgroup.secondhand.core.data.repositories.product

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.firstgroup.secondhand.core.database.product.ProductLocalDataSource
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import com.firstgroup.secondhand.core.model.Banner
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.network.product.ProductRemoteDataSource
import com.firstgroup.secondhand.core.network.product.model.ProductDto
import com.firstgroup.secondhand.core.network.product.model.ProductRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
) : ProductRepository {

    @ExperimentalPagingApi
    override fun getProductsAsBuyer(): Flow<PagingData<ProductEntity>> {
        // TODO: Use it when caching fix
//        return Pager(
//            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
//            remoteMediator = ProductRemoteMediator(
//                remoteDataSource = remoteDataSource,
//                localDataSource = localDataSource,
//            ),
//            pagingSourceFactory = {
//                localDataSource.getCachedProducts()
//            }
//        ).flow

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                ProductPagingSource(remoteDataSource)
            }
        ).flow
    }

    override fun getProductsByCategory(categoryId: Int): Flow<PagingData<ProductDto>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            ProductByCategoryPagingSource(categoryId, remoteDataSource)
        }
    ).flow

    override fun searchProducts(query: String): Flow<PagingData<ProductDto>> = Pager(
        config = PagingConfig(
            pageSize = NETWORK_PAGE_SIZE,
            enablePlaceholders = false,
        ),
        pagingSourceFactory = {
            SearchProductPagingSource(query, remoteDataSource)
        }
    ).flow

    override suspend fun getProductByIdAsBuyer(id: Int): Product =
        remoteDataSource.getProductByIdAsBuyer(id).mapToDomainModel()

    override suspend fun deleteCachedProducts() {
        localDataSource.clearCachedProducts()
    }

    private suspend fun refreshCategoryCache() {
        val remoteData = remoteDataSource.getCategories()
        localDataSource.cacheAllCategories(
            remoteData.map { category ->
                CategoryEntity(
                    id = category.id,
                    name = category.name,
                )
            }
        )
    }


    /**
     * Seller
     */
    override suspend fun getProductsAsSeller(): List<Product> {
        return remoteDataSource.getProductsAsSeller().map {
            it.mapToDomainModel()
        }
    }

    override suspend fun getProductByIdAsSeller(id: Int): Product =
        remoteDataSource.getProductByIdAsSeller(id).mapToDomainModel()

    override suspend fun addNewProduct(
        productRequest: ProductRequest
    ): Product = remoteDataSource.addNewProduct(productRequest).mapToDomainModel()

    override suspend fun getCategories(): List<Category> {
        return localDataSource.getCachedCategories()
            .map { category ->
                category.mapToDomainModel()
            }
    }

    override suspend fun loadCategories() {
        try {
            refreshCategoryCache()
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException,
                is ConnectException,
                is HttpException -> {
                    if (localDataSource.getCachedCategories().isEmpty())
                        throw Exception(
                            "Something went wrong. No Data Available"
                        )
                }
                else -> throw e
            }
        }
    }

    override suspend fun deleteCachedCategories() {
        localDataSource.deleteCachedCategories()
    }

    override suspend fun deleteWishlist() {
        localDataSource.deleteWishlist()
    }

    override suspend fun getBanner(): List<Banner> = remoteDataSource.getBanners().map {
        it.mapToDomainModel()
    }

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val NETWORK_PAGE_SIZE = 20
    }

}