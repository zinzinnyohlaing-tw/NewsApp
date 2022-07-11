package eu.hanna.movieapp.repository

import eu.hanna.movieapp.api.RetrofitInstance
import eu.hanna.movieapp.database.ArticleDatabase
import eu.hanna.movieapp.models.Article

class NewsRepository(val db: ArticleDatabase) {

    suspend fun getTopNews(countryCode: String,categoryCode:String,pageNumber: Int) =
        RetrofitInstance.api.getTopHeadLines(countryCode,categoryCode,pageNumber)

    suspend fun searchNews(searchQuery: String,pageNumber: Int) =
        RetrofitInstance.api.searchNews(searchQuery,pageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getSaveNews() = db.getArticleDao().getAllArticles()

}