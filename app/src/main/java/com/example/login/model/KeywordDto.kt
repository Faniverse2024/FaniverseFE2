package com.example.login.model

data class KeywordDto(
    val id: Long?,
    val word: String
)

data class KeywordProductDto(
    val id: Long,
    val title: String,
    val content: String,
    val category: String
)
