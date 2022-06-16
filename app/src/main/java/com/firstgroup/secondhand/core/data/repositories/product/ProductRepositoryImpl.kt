package com.firstgroup.secondhand.core.data.repositories.product

import com.firstgroup.secondhand.core.database.product.ProductLocalDataSource
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.network.product.ProductRemoteDataSource
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

class ProductRepositoryImpl(
    private val remoteDataSource: ProductRemoteDataSource,
    private val localDataSource: ProductLocalDataSource,
) : ProductRepository {

    override suspend fun getProductsAsBuyer(token: String): List<Product> {
        loadBuyerProducts(token)
        return localDataSource.getCachedProducts().map {
            it.mapToDomainModel()
        }
    }

    private suspend fun loadBuyerProducts(token: String) {
        try {
            refreshCache(token)
        } catch (e: Exception) {
            when (e) {
                is UnknownHostException,
                is ConnectException,
                is HttpException -> {
                    if (localDataSource.getCachedProducts().isEmpty())
                        throw Exception(
                            "Something went wrong. No Data Available"
                        )
                }
                else -> throw e
            }
        }
    }

    private suspend fun refreshCache(token: String) {
        val remoteData = remoteDataSource.getProductsAsBuyer(token)
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
                    categories = product.categories.map { category ->
                        CategoryEntity(
                            id = category.id,
                            name = category.name
                        )
                    }
                )
            }
        )
    }
}