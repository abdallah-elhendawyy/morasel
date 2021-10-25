package com.example.morasel.di

import android.app.Application
import androidx.room.Room
import com.example.morasel.room.ArticleDataBase
import com.example.morasel.room.Article_Dao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object Db_module {

    @Provides
    @Singleton
    fun provide_dp(application: Application):ArticleDataBase=
             Room.databaseBuilder(
                application,
                ArticleDataBase::class.java,
                "article_db.db"
                 ).build()

    @Provides
    @Singleton
    fun provideDao(articleDataBase: ArticleDataBase):Article_Dao=
        articleDataBase.getArticleDao()

}