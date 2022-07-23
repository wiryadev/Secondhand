package com.firstgroup.secondhand.core.network.order.model


import com.firstgroup.secondhand.core.model.Buyer
import com.firstgroup.secondhand.core.model.Order
import com.firstgroup.secondhand.core.model.Product
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetOrderDto(
    @Json(name = "buyer_id")
    val buyerId: Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "price")
    val price: Int,
    @Json(name = "transaction_date")
    val transactionDate: String?,
    @Json(name = "Product")
    val product: ProductData,
    @Json(name = "product_id")
    val productId: Int,
    @Json(name = "status")
    val status: String,
    @Json(name = "User")
    val user: User,
) {
    @JsonClass(generateAdapter = true)
    data class ProductData(
        @Json(name = "base_price")
        val basePrice: Int,
        @Json(name = "description")
        val description: String?,
        @Json(name = "image_name")
        val imageName: String?,
        @Json(name = "image_url")
        val imageUrl: String?,
        @Json(name = "location")
        val location: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "status")
        val status: String,
        @Json(name = "user_id")
        val userId: Int,
        @Json(name = "User")
        val user: User,
    ) {
        @JsonClass(generateAdapter = true)
        data class User(
            @Json(name = "address")
            val address: String,
            @Json(name = "city")
            val city: String?,
            @Json(name = "email")
            val email: String,
            @Json(name = "full_name")
            val fullName: String,
            @Json(name = "id")
            val id: Int,
            @Json(name = "phone_number")
            val phoneNumber: String
        )
    }

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "id")
        val id: Int,
        @Json(name = "address")
        val address: String,
        @Json(name = "city")
        val city: String?,
        @Json(name = "email")
        val email: String,
        @Json(name = "full_name")
        val fullName: String,
        @Json(name = "phone_number")
        val phoneNumber: String,
        @Json(name = "image_url")
        val imageUrl: String?
    )

    fun mapToDomain() = Order(
        id = id,
        bidPrice = price,
        status = status,
        transactionDate = transactionDate.orEmpty(),
        product = Product(
            id = productId,
            name = product.name,
            description = product.description.orEmpty(),
            price = product.basePrice,
            imageUrl = product.imageUrl.orEmpty(),
            location = product.location,
            userId = product.userId,
            category = "",
            seller = Product.Seller(
                id = product.user.id,
                name = product.user.fullName,
                imageUrl = "",
                city = product.user.city ?: "No Location"
            )
        ),
        buyer = Buyer(
            id = user.id,
            fullName = user.fullName,
            email = user.email,
            phoneNumber = user.phoneNumber,
            address = user.address,
            imageUrl = user.imageUrl.orEmpty(),
            city = user.city.orEmpty()
        )
    )
}