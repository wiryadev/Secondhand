package com.firstgroup.secondhand.core.database.product.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.firstgroup.secondhand.core.model.Product

@Entity(tableName = "product_cache")
data class ProductEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,

    @ColumnInfo(name = "location")
    val location: String?,

    @ColumnInfo(name = "user_id")
    val userId: Int,

    @ColumnInfo(name = "status")
    val status: String,

    @ColumnInfo(name = "category")
    val category: String,
) {
    fun mapToDomainModel() = Product(
        id = id,
        name = name,
        description = description,
        price = price,
        imageUrl = imageUrl,
        location = location,
        userId = userId,
        status = status,
        category = category,
    )
}
