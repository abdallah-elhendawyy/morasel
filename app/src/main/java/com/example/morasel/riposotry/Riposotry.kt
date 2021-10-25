package com.example.morasel.riposotry

import com.example.morasel.api.NewsApi
import com.example.morasel.pojo.Article
import com.example.morasel.room.Article_Dao
import javax.inject.Inject

class Riposotry
@Inject constructor(private  val newsApi:NewsApi,private val articleDao: Article_Dao){

    suspend fun getBreakingNews(pageNumber: Int)= newsApi.getBreakingNews("us",pageNumber,)

    suspend fun searchForNews( searchQuery: String,pageNumber: Int) =newsApi.searchForNews( searchQuery,pageNumber)

    suspend fun inseretintodp(article: Article)=articleDao.inseret(article)

     fun getAllSaedArticles()=articleDao.getAllArticles()

    suspend fun deleteSavedArticle(article: Article)=articleDao.deleteArticle(article)

}