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
import com.firstgroup.secondhand.core.network.product.model.ProductRequest
import com.firstgroup.secondhand.core.network.product.model.filterProductPolicy
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
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            remoteMediator = ProductRemoteMediator(
                remoteDataSource = remoteDataSource,
                localDataSource = localDataSource,
            ),
            pagingSourceFactory = {
                localDataSource.getCachedProducts()
            }
        ).flow
    }

    override suspend fun getProductsByCategory(categoryId: Int): List<Product> =
        remoteDataSource.getProductsByCategory(categoryId)
            .filter(::filterProductPolicy)
            .map { it.mapToDomainModel() }

    override suspend fun searchProducts(query: String): List<Product> =
        remoteDataSource.searchProducts(query)
            .filter(::filterProductPolicy)
            .map { it.mapToDomainModel() }

    override suspend fun getProductByIdAsBuyer(id: Int): Product =
        remoteDataSource.getProductByIdAsBuyer(id).mapToDomainModel()

//    override suspend fun loadBuyerProducts() {
//        try {
//            refreshProductCache()
//        } catch (e: Exception) {
//            // do nothing
//        }
//    }

    override suspend fun deleteCachedProducts() {
        localDataSource.deleteCachedProducts()
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

}

private const val NETWORK_PAGE_SIZE = 30