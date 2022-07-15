package com.firstgroup.secondhand.core.network.product.model


import com.firstgroup.secondhand.core.database.product.entity.ProductEntity
import com.firstgroup.secondhand.core.model.Product
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductDto(
    @Json(name = "base_price")
    val basePrice: Int?,
    @Json(name = "Categories")
    val categories: List<CategoryDto>,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image_name")
    val imageName: String?,
    @Json(name = "image_url")
    val imageUrl: String?,
    @Json(name = "location")
    val location: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "status")
    val status: String?,
    @Json(name = "updatedAt")
    val updatedAt: String,
    @Json(name = "user_id")
    val userId: Int,
) {
    fun mapToDomainModel() = Product(
        id = id,
        name = name.orEmpty(),
        description = description.orEmpty(),
        price = basePrice ?: 0,
        imageUrl = imageUrl.orEmpty(),
        location = location.orEmpty(),
        userId = userId,
        category = try {
            categories[0].name
        } catch (e: Exception) {
            "No Categories!"
        }
    )

    fun mapToEntityModel() = ProductEntity(
        id = id,
        name = name.orEmpty(),
        description = description.orEmpty(),
        price = basePrice ?: 0,
        imageUrl = imageUrl.orEmpty(),
        location = location.orEmpty(),
        userId = userId,
        category = try {
            categories[0].name
        } catch (e: Exception) {
            ""
        },
    )
}

fun filterProductPolicy(productDto: ProductDto): Boolean {
    return productDto.imageUrl != null
            && !productDto.name.isNullOrEmpty()
            && productDto.basePrice != null
}