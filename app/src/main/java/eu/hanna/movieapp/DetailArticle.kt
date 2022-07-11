package eu.hanna.movieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import eu.hanna.movieapp.adapter.NewsAdapter
import eu.hanna.movieapp.database.ArticleDatabase
import eu.hanna.movieapp.databinding.ActivityArticleDetailsBinding
import eu.hanna.movieapp.fragment.TopNewsFragment
import eu.hanna.movieapp.models.Article
import eu.hanna.movieapp.repository.NewsRepository
import eu.hanna.movieapp.viewmodel.NewsViewModel
import eu.hanna.movieapp.viewmodel.NewsViewModelProviderFactory

class DetailArticle : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailsBinding

    private lateinit var url: String

    lateinit var viewModel: NewsViewModel

    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRepository = NewsRepository(ArticleDatabase(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(NewsViewModel::class.java)

        getArticleDetailURL()

        binding.floationbutton.setOnClickListener {
                viewModel.saveArticle(article)
               Toast.makeText(this@DetailArticle,"Save!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getArticleDetailURL(){
        showProgressBar()
        hideProgressBar()
        val intent = intent
        url = intent.getStringExtra(TopNewsFragment.URL)!!
        binding.articleWebView.webViewClient = WebViewClient()
        binding.articleWebView.loadUrl(url)
    }

    private fun hideProgressBar(){
        binding.loadingProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.loadingProgress.visibility = View.VISIBLE
    }
}