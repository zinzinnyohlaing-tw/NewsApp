package eu.hanna.movieapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eu.hanna.movieapp.repository.NewsRepository

class NewsViewModelProviderFactory(val newsRepository: NewsRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}