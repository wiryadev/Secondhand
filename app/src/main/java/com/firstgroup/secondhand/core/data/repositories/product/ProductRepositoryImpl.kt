package com.firstgroup.secondhand.core.data.repositories.product

import com.firstgroup.secondhand.core.database.product.ProductLocalDataSource
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import com.firstgroup.secondhand.core.model.Banner
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.network.product.ProductRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
) : ProductRepository {

    override fun getProductsAsBuyer(): Flow<List<Product>> {
        return localDataSource.getCachedProducts().map { products ->
            products.map { it.mapToDomainModel() }
        }
    }

    override suspend fun loadBuyerProducts() {
        try {
            refreshProductCache()
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException,
                is ConnectException,
                is HttpException -> {
                    if (localDataSource.getCachedProducts().first().isEmpty())
                        throw Exception(
                            "Something went wrong. No Data Available"
                        )
                }
                else -> throw e
            }
        }
    }

    override fun getCategories(): Flow<List<Category>> {
        return localDataSource.getCachedCategories().map { categories ->
            categories.map { it.mapToDomainModel() }
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
                    if (localDataSource.getCachedCategories().first().isEmpty())
                        throw Exception(
                            "Something went wrong. No Data Available"
                        )
                }
                else -> throw e
            }
        }
    }

    override suspend fun getBanner(): List<Banner> =
        remoteDataSource.getBanners().map { it.mapToDomainModel() }

    /**
     * Only buyer side of products that needs to be cached
     */
    private suspend fun refreshProductCache() {
        val remoteData = remoteDataSource.getProductsAsBuyer()
        localDataSource.cacheAllProducts(
            remoteData.map { product ->
                ProductEntity(
                    id = product.id,
                    name = product.name,
                    description = product.description,
                    price = product.basePrice,
                    imageUrl = product.imageUrl,
                    location = product.location,
                    userId = product.userId,
                    status = product.status,
                    category = product.categories[0].name,
                )
            }
        )
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
}