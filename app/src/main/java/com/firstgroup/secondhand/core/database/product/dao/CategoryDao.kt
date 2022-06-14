package com.firstgroup.secondhand.core.database.product.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM product_categories")
    fun getCategoriesStream(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndReplace(categories: List<CategoryEntity>): List<Long>

    @Query("DELETE FROM product_categories")
    suspend fun deleteAll()

}