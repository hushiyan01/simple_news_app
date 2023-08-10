package com.myworkshop.simple_news_app.views.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.VolleyError
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.myworkshop.simple_news_app.R
import com.myworkshop.simple_news_app.databinding.NewsItemBinding
import com.myworkshop.simple_news_app.model.db.NewsDao
import com.myworkshop.simple_news_app.model.db.toStringWithComma
import com.myworkshop.simple_news_app.model.News
import com.myworkshop.simple_news_app.model.VolleyImageCaching
import com.myworkshop.simple_news_app.model.VolleyImageCaching.imageLoader
import com.myworkshop.simple_news_app.views.NewsDetailFragment
import com.squareup.picasso.Picasso


class NewsAdapter(
    val news: List<News>,
    val context: Context,
    private val newsDao: NewsDao,
    private val onItemClickListener: OnItemClickListener
//    private var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder>() {

    private lateinit var binding: NewsItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = NewsItemBinding.inflate(layoutInflater, parent, false)
        return NewsItemViewHolder(binding)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bind(position)
        holder.itemView.findViewById<ConstraintLayout>(R.id.upper_portion).setOnClickListener{
            val bundle = Bundle().apply {
                putString("title", news[position].newsAttributes.title)
                putString("authors", news[position].newsAttributes.author)
                putString("body", news[position].newsAttributes.description)
                putString("image_url", news[position].newsAttributes.image)
            }
            val fragment = NewsDetailFragment()
            fragment.arguments = bundle
            onItemClickListener.onItemClick(fragment)
        }
    }


    inner class NewsItemViewHolder(binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val authors = binding.tvAuthorName
        private val title = binding.tvTitle
        private val publishedTime = binding.tvPublishedTime
        private val body = binding.tvBody
        private val publisher = binding.tvPublisher
        private val background = binding.upperPortion
        private val button = binding.btnSaveToFavorite


        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            title.text = news[position].newsAttributes.title
            authors.text = "published by: \n"+news[position].newsAttributes.author
            publisher.text = "category: " + news[position].newsAttributes.category.toStringWithComma()
            body.text = news[position].newsAttributes.description
            publishedTime.text = news[position].newsAttributes.published
            news[position].newsAttributes.image
            if(news[position].newsAttributes.image.isNotEmpty()){
                Picasso.get()
                    .load(news[position].newsAttributes.image)
                    .into(object : com.squareup.picasso.Target {
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                            background.background = BitmapDrawable(context.resources, bitmap)
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                        }

                    })
            }

//            loadImageToLayoutBackground(news[position].newsAttributes.image, background)

            if (news[position].isFavorite) {
                button.apply {
                    setBackgroundResource(R.drawable.favorite_true)
//                    isEnabled = false
                    setOnClickListener {
                        newsDao.delete(news[position].newsAttributes.id)
                        Toast.makeText(context, "Successfully unfollowed!", Toast.LENGTH_SHORT).show()
                        news[position].isFavorite = false
                        notifyDataSetChanged()
                        setBackgroundResource(R.drawable.favorite_false)
                    }
                }
            }else{
                button.apply{
                    setBackgroundResource(R.drawable.favorite_false)

                    setOnClickListener {
                        newsDao.save(news[position].newsAttributes)
                        Toast.makeText(context, "News added to you favorites!", Toast.LENGTH_SHORT)
                            .show()
                        setBackgroundResource(R.drawable.favorite_true)

                        news[position].isFavorite = true
                        notifyDataSetChanged()
                    }
                }

            }
        }

//        private fun loadImageToLayoutBackground(imageUrl: String, layout: ConstraintLayout) {
//
//            val imageListener = ImageLoader.ImageListener(
//                { response ->
//                    if (response != null) {
//                        val drawable = BitmapDrawable(resources, response.bitmap)
//                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                            layout.background = drawable
//                        } else {
//                            layout.setBackgroundDrawable(drawable)
//                        }
//                    }
//                },
//                0,  // 默认图像资源 ID（加载失败时使用）
//                0   // 错误图像资源 ID（加载失败时使用）
//            )
//
//            imageLoader.get(imageUrl, imageListener)
//
//        }
    }
}