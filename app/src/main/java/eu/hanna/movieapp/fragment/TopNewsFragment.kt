package eu.hanna.movieapp.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import eu.hanna.movieapp.DetailArticle
import eu.hanna.movieapp.MainActivity
import eu.hanna.movieapp.adapter.NewsAdapter
import eu.hanna.movieapp.databinding.FragmentTopNewsBinding
import eu.hanna.movieapp.util.Resource
import eu.hanna.movieapp.viewmodel.NewsViewModel

class TopNewsFragment : Fragment() {

    private lateinit var binding: FragmentTopNewsBinding

    lateinit var newsAdapter: NewsAdapter

    lateinit var viewModel: NewsViewModel

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
        binding = FragmentTopNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        // observe the livedata from viewmodel
        viewModel.topHeadLinesNews.observe(viewLifecycleOwner, Observer { response ->
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

    // set up the recyclerView
    private fun setupRecyclerView(){
        newsAdapter = NewsAdapter()
        binding.rvTopheadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    // function on itemClicklistener in recyclerview
    private fun onArticleItemClick() {
        newsAdapter.onItemClick = { articles ->
            var intent = Intent(context, DetailArticle::class.java)
            intent.putExtra(URL,articles.url)
            startActivity(intent)
        }
    }

}
