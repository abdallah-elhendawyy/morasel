package com.example.morasel.api

import com.example.morasel.pojo.NewsResponce
import com.example.morasel.util.Constant.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    //https://newsapi.org/
// v2/top-headlines?=us&apiKey=83841ee6b29a4e7d946e273f0fa46321

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countywide:String="us",
        @Query("page")
        pageNumber: Int ,
        @Query("apiKey")
        apiKey: String = API_KEY
    ):Response<NewsResponce>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int ,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponce>
}