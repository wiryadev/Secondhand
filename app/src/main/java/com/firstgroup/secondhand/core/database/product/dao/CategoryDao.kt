package com.firstgroup.secondhand.core.database.product.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity

@Dao
interface CategoryDao {

    @Query("SELECT * FROM product_categories")
    suspend fun getCachedCategories(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(categories: List<CategoryEntity>)

    @Query("DELETE FROM product_categories")
    suspend fun deleteAll()

}