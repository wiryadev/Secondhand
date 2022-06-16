package com.firstgroup.secondhand.core.database.product.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firstgroup.secondhand.core.database.product.entity.ProductEntity

@Dao
interface ProductCacheDao {

    @Query("SELECT * FROM product_cache")
    suspend fun getAll(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(products: List<ProductEntity>)

}