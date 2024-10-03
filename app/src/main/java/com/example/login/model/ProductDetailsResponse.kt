package com.example.login.model

import java.time.LocalDateTime

data class ProductDetailsResponse(
    val userName: String,
    val imageUrl: String,
    val title: String,
    val category: String,
    val content: String,
    val startingPrice: Double?,
    val endDate: LocalDateTime?,
    val status: String,
    val finalPrice: Double?,
    val price: Double?,
    val createdAt: LocalDateTime?,
    val winningBid: AuctionBidResponse?
)