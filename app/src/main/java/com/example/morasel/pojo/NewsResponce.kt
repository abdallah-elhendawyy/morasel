package com.example.morasel.pojo

data class NewsResponce(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)