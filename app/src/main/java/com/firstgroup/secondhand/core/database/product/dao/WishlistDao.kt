package com.firstgroup.secondhand.core.database.product.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.firstgroup.secondhand.core.database.product.entity.WishlistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    @Query("SELECT * FROM product_wishlist")
    fun getWishlistStream(): Flow<List<WishlistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAndReplace(wishlist: List<WishlistEntity>): List<Long>

    @Query("DELETE FROM product_wishlist")
    suspend fun deleteAll()

}