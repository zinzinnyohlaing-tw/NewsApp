package eu.hanna.movieapp.api

import eu.hanna.movieapp.models.NewsResponse
import eu.hanna.movieapp.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getTopHeadLines(
        @Query("country")
        countryCode: String,
        @Query("category")
        categoryCode: String,
        @Query("page")
        pageNumber: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ) : Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}