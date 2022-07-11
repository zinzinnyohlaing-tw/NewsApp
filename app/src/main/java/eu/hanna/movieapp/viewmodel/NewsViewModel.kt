package eu.hanna.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.hanna.movieapp.models.Article
import eu.hanna.movieapp.models.NewsResponse
import eu.hanna.movieapp.repository.NewsRepository
import eu.hanna.movieapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(val newsRepository: NewsRepository) : ViewModel() {

    val topHeadLinesNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var topHeadLinespages = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1

    init {
        getTopHeadLinesNews("in","entertainment")
    }

    fun getTopHeadLinesNews(countryCode:String,categoryCode:String) = viewModelScope.launch {
        topHeadLinesNews.postValue(Resource.Loading())

        val response = newsRepository.getTopNews(countryCode,categoryCode,topHeadLinespages)
        topHeadLinesNews.postValue(networkHandleForTopHeadLines(response))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
      searchNews.postValue(Resource.Loading())

      val response = newsRepository.searchNews(searchQuery,searchNewsPage)
      searchNews.postValue(networkHandleForTopHeadLines(response))

    }

    private fun networkHandleForTopHeadLines(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun getSaveNews() = viewModelScope.launch {
        newsRepository.getSaveNews()
    }
}