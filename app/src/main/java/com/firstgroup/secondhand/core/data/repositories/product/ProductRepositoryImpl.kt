package com.firstgroup.secondhand.core.data.repositories.product

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.firstgroup.secondhand.core.database.product.ProductLocalDataSource
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import com.firstgroup.secondhand.core.model.Banner
import com.firstgroup.secondhand.core.model.Category
import com.firstgroup.secondhand.core.model.Product
import com.firstgroup.secondhand.core.network.product.ProductRemoteDataSource
import com.firstgroup.secondhand.core.network.product.model.ProductRequest
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

    override fun getProductsAsBuyer(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                localDataSource.getCachedProducts()
            }
        ).flow.map { pagingData ->
            pagingData.map { it.mapToDomainModel() }
        }
    }

    override suspend fun getProductByIdAsBuyer(id: Int): Product =
        remoteDataSource.getProductByIdAsBuyer(id).mapToDomainModel()

    override suspend fun loadBuyerProducts() {
        try {
            refreshProductCache()
        } catch (e: Exception) {
            // do nothing
        }
    }

    override suspend fun deleteCachedProducts() {
        localDataSource.deleteCachedProducts()
    }

    /**
     * Only buyer side of products that needs to be cached
     */
    private suspend fun refreshProductCache() {
        val remoteData = remoteDataSource.getProductsAsBuyer()
        localDataSource.cacheAllProducts(
            remoteData
                .filter { product ->
                    product.imageUrl != null
                            && !product.name.isNullOrEmpty()
                            && product.basePrice != null
                }
                .map { filteredProduct ->
                    ProductEntity(
                        id = filteredProduct.id,
                        name = filteredProduct.name!!,
                        description = filteredProduct.description,
                        price = filteredProduct.basePrice!!,
                        imageUrl = filteredProduct.imageUrl!!,
                        location = filteredProduct.location,
                        userId = filteredProduct.userId,
                        category = try {
                            val categories = filteredProduct.categories.map { it.name }
                            categories.joinToString(separator = ", ")
                        } catch (e: Exception) {
                            "No Categories"
                        },
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