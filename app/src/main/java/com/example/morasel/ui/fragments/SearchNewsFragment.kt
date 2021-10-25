package com.example.morasel.ui.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.morasel.R
import com.example.morasel.adapters.NewsAdapter
import com.example.morasel.util.Constant.SEARCH_NEWS_TIME_DELAY
import com.example.morasel.util.Rescourseee
import com.example.morasel.viewmodel.NewsVIewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchNewsFragment:Fragment(R.layout.fragment_search_news) {
    private val viewModel: NewsVIewModel by viewModels()
    var newsAdapter: NewsAdapter = NewsAdapter(context)
    lateinit var recyclerView: RecyclerView
    lateinit var paginationProgressBar: ProgressBar
    lateinit var etSearch:EditText


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paginationProgressBar=view.findViewById(R.id.paginationProgressBar)
        etSearch=view.findViewById(R.id.etSearch)
        recyclerView=view.findViewById(R.id.rvSearchNews)
        setupwithrv()
            newsAdapter.setOnItemClickListener {
                val bundle=Bundle().apply {
                    putSerializable("article",it)
                }
                findNavController().navigate(
                   R.id.action_searchNewsFragment_to_articleFragment,
                        bundle
                )
            }


        var job: Job? = null
        etSearch.addTextChangedListener{ editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString())
                    }
                }
            }
        }


        viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Rescourseee.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Rescourseee.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(ContentValues.TAG, "An error occured: $message")
                    }
                }
                is Rescourseee.Loading -> {
                    showProgressBar()
                }
            }
        })





    }



    private fun showProgressBar() {
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupwithrv(){
        // newsAdapter= NewsAdapter()
        recyclerView.adapter=newsAdapter
        recyclerView.layoutManager= LinearLayoutManager(activity)

    }
}