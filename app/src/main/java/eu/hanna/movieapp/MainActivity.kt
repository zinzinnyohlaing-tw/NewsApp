package eu.hanna.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import eu.hanna.movieapp.database.ArticleDatabase
import eu.hanna.movieapp.repository.NewsRepository
import eu.hanna.movieapp.viewmodel.NewsViewModel
import eu.hanna.movieapp.viewmodel.NewsViewModelProviderFactory

// API KEY 54b4bf05a2d60e8612ba2d55c62e91b1
// To load the images https://image.tmdb.org/t/p/w500/jrgifaYeUtTnaH7NF5Drkgjg2MB.jpg
// .load("https://image.tmdb.org/t/p/w500"+mData.get(position).getImg())
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)
        //viewModel = ViewModelProviders.of(this,newsRepository).get(NewsViewModel::class.java)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = Navigation.findNavController(this, R.id.newsNavHostFragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)
    }
}

//tv_popular rv_topheadlines paginationProgressBar