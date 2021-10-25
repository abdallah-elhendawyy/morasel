package com.example.morasel.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.morasel.R
import com.example.morasel.adapters.NewsAdapter
import com.example.morasel.viewmodel.NewsVIewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class savedNewsFrament:Fragment(R.layout.fragment_saved_news) {
    private val viewmodel :NewsVIewModel by viewModels()
    var newsAdapter=NewsAdapter(context)
    lateinit var recyclerView: RecyclerView
    lateinit var paginationProgressBar: ProgressBar
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.rvSavedNews)
        setupwithrv()
        newsAdapter.setOnItemClickListener {
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                    R.id.action_savedNewsFrament_to_articleFragment2,
                    bundle
            )
        }
        viewmodel.getallArtiels().observe(viewLifecycleOwner, Observer { articls ->
            newsAdapter.differ.submitList(articls)
        } )

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewmodel.deleteArticle(article)
                Snackbar.make(view, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewmodel.inseretArticle(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(recyclerView)
        }





    }




    private fun setupwithrv(){
        // newsAdapter= NewsAdapter()
        recyclerView.adapter=newsAdapter
        recyclerView.layoutManager= LinearLayoutManager(activity)

    }
}