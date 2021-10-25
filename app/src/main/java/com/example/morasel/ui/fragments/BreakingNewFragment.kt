package com.example.morasel.ui.fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.morasel.R
import com.example.morasel.adapters.NewsAdapter
import com.example.morasel.util.Rescourseee
import com.example.morasel.viewmodel.NewsVIewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewFragment :Fragment(R.layout.fragment_breaking_news){
    private val viewModel: NewsVIewModel by viewModels()
     var newsAdapter: NewsAdapter=NewsAdapter(context)
    lateinit var recyclerView: RecyclerView
    lateinit var paginationProgressBar:ProgressBar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        paginationProgressBar=view.findViewById(R.id.paginationProgressBar)
        recyclerView=view.findViewById(R.id.rvBreakingNews)
        setupwithrv()
        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                    R.id.action_breakingNewFragment_to_articleFragment,
                    bundle
            )
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
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
                        Log.e(TAG, "An error occured: $message")
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
        recyclerView.layoutManager=LinearLayoutManager(activity)

    }
}