package com.firstgroup.secondhand.core.database.product.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.firstgroup.secondhand.core.model.Category

@Entity(tableName = "product_categories")
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,
) {
    fun mapToDomainModel() = Category(
        id = id,
        name = name,
    )
}
