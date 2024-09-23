package com.example.login.model

data class WishlistDto(
    val id: Long,
    val productId: Long,
    val productName: String,
    val price: Double
)

data class WishlistRequestDto(
    val productId: Long
)
