package com.firstgroup.secondhand.core.database.product.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val productId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
