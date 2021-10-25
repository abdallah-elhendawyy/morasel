package com.example.morasel.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.morasel.R
import com.example.morasel.databinding.ItemArticlePreviewBinding
import com.example.morasel.pojo.Article

class NewsAdapter():RecyclerView.Adapter<NewsAdapter.viewHolder>() {
    private var mContext: Context? = null
    constructor(mContext: Context?) : this() {  this.mContext = mContext }

    inner class viewHolder(val binding: ItemArticlePreviewBinding):RecyclerView.ViewHolder(binding.root)

    ///diffutill
    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

////

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            ItemArticlePreviewBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_article_preview,
                    parent,
                    false
                )
            )
        )
    }


    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val article = differ.currentList[position]
         holder.binding.apply {
             tvSource.text = article.source.name
             tvTitle.text = article.title
             tvDescription.text = article.description
             tvPublishedAt.text = article.publishedAt
           //  mContext?.let { Glide.with(it).load(article.urlToImage).into(ivArticleImage) }
             ivArticleImage.load(article.urlToImage){
                 crossfade(true)
                 crossfade(1000)
             }

         }
         holder.itemView.setOnClickListener{
                onItemClickListener?.let { it(article) }
            }
    }

    override fun getItemCount(): Int {
      return differ.currentList.size
    }



    private var onItemClickListener: ((Article) -> Unit)? = null
    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
    }