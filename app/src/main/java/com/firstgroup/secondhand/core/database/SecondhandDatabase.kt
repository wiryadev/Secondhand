package com.firstgroup.secondhand.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.firstgroup.secondhand.core.database.product.dao.CategoryDao
import com.firstgroup.secondhand.core.database.product.dao.ProductCacheDao
import com.firstgroup.secondhand.core.database.product.dao.WishlistDao
import com.firstgroup.secondhand.core.database.product.entity.CategoryEntity
import com.firstgroup.secondhand.core.database.product.entity.WishlistEntity

@Database(
    entities = [
        CategoryEntity::class,
        WishlistEntity::class,
    ],
    version = 1,
)
abstract class SecondhandDatabase : RoomDatabase() {
    abstract fun productCacheDao(): ProductCacheDao
    abstract fun categoryDao(): CategoryDao
    abstract fun wishlistDao(): WishlistDao
}