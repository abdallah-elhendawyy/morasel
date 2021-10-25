package com.example.morasel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.example.morasel.pojo.Article
import com.example.morasel.pojo.NewsResponce
import com.example.morasel.riposotry.Riposotry
import com.example.morasel.util.Rescourseee
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsVIewModel @Inject constructor(private val riposotry: Riposotry)
:ViewModel(){
    init {
        getBreakingNews()
    }
    val breakingNews: MutableLiveData<Rescourseee<NewsResponce>> = MutableLiveData()
   private var breakingNewsPage = 1

    val searchNews: MutableLiveData<Rescourseee<NewsResponce>> = MutableLiveData()
  private  var searchNewsPage = 1
    
    fun getBreakingNews()=viewModelScope.launch {
//        breakingNews.postValue(Rescourseee.Loading())
        val response = riposotry.getBreakingNews( breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))

    }

    fun searchNews(q:String)=viewModelScope.launch {
        val responce=riposotry.searchForNews(q,searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(responce))
    }


    fun inseretArticle(article :Article)=viewModelScope.launch {
        riposotry.inseretintodp(article)
    }

    fun  deleteArticle(article: Article)=viewModelScope.launch { riposotry.deleteSavedArticle(article) }

    fun getallArtiels()=riposotry.getAllSaedArticles()

    private fun handleBreakingNewsResponse(response: Response<NewsResponce>): Rescourseee<NewsResponce>? {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Rescourseee.Success(resultResponse)
            }
        }
        return Rescourseee.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponce>): Rescourseee<NewsResponce>? {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Rescourseee.Success(resultResponse)
            }
        }
        return Rescourseee.Error(response.message())
    }
}