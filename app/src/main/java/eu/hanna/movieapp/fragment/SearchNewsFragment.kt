package eu.hanna.movieapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import eu.hanna.movieapp.DetailArticle
import eu.hanna.movieapp.MainActivity
import eu.hanna.movieapp.R
import eu.hanna.movieapp.adapter.NewsAdapter
import eu.hanna.movieapp.databinding.FragmentSearchNewsBinding
import eu.hanna.movieapp.util.Constants.Companion.DELAY_TIME
import eu.hanna.movieapp.util.Resource
import eu.hanna.movieapp.viewmodel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding

    lateinit var viewModel: NewsViewModel

    lateinit var newsAdapter: NewsAdapter

    companion object{
        const val URL = "eu.hanna.movieapp.fragment.url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        delayLoadingTime()

        // observe the livedata from viewmodel
        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()

                    // checking if the response data is not null
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()

                    response.message?.let { message ->
                        Log.e("headlines", "An error occured: $message")
                    }

                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        onArticleItemClick()
    }

    private fun delayLoadingTime() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(DELAY_TIME)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }

                }
            }
        }
    }

    // set up the recyclerView
    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvSearchNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    // function on itemClicklistener in recyclerview
    private fun onArticleItemClick() {
        newsAdapter.onItemClick = { articles ->
            var intent = Intent(context, DetailArticle::class.java)
            intent.putExtra(TopNewsFragment.URL,articles.url)
            startActivity(intent)
        }
    }
    private fun hideProgressBar() {
        binding.loadingSearchProgress.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.loadingSearchProgress.visibility = View.VISIBLE
    }
}