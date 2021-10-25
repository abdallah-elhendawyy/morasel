package com.example.morasel.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.morasel.pojo.Article

@Dao
interface Article_Dao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inseret(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)

}