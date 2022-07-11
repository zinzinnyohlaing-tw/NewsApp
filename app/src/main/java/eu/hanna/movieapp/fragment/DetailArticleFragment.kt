package eu.hanna.movieapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.hanna.movieapp.MainActivity
import eu.hanna.movieapp.R
import eu.hanna.movieapp.databinding.FragmentDetailArticleBinding
import eu.hanna.movieapp.databinding.FragmentSearchNewsBinding
import eu.hanna.movieapp.viewmodel.NewsViewModel


class DetailArticleFragment : Fragment() {

    private lateinit var binding: FragmentDetailArticleBinding

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }
}