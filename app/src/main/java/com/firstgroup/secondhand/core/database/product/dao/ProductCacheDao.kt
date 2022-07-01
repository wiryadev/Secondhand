package com.firstgroup.secondhand.core.database.product.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity

@Dao
interface ProductCacheDao {

    @Query("SELECT * FROM product_cache")
    fun getCachedProducts(): PagingSource<Int, ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(products: List<ProductEntity>)

    @Query("DELETE FROM product_cache")
    suspend fun deleteAll()

}