package com.myworkshop.simple_news_app.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.myworkshop.simple_news_app.databinding.FragmentFavoriteNewsBinding
import com.myworkshop.simple_news_app.model.News
import com.myworkshop.simple_news_app.model.NewsResponse
import com.myworkshop.simple_news_app.model.db.NewsDBHelper
import com.myworkshop.simple_news_app.model.db.NewsDao
import com.myworkshop.simple_news_app.presenter.FavoriteNewsPresenter
import com.myworkshop.simple_news_app.presenter.MVPNews
import com.myworkshop.simple_news_app.views.adapters.NewsAdapter
import com.myworkshop.simple_news_app.views.adapters.OnItemClickListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FavoritesNewsFragment(private val onItemClickListener: OnItemClickListener) : Fragment(), MVPNews.FavoritesView{
    private lateinit var binding: FragmentFavoriteNewsBinding
    private lateinit var newsDao: NewsDao
    private lateinit var presenter: FavoriteNewsPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteNewsBinding.inflate(layoutInflater, container, false)
        newsDao = NewsDao(dbHelper = NewsDBHelper(requireContext()))
        presenter = FavoriteNewsPresenter(dao = newsDao, this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadNewsData()
    }


    private fun loadNewsData() {
        presenter.getNews()
    }


    override fun loadFromDao(list: List<News>) {
        binding.rvFavoriteNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = NewsAdapter(list, requireContext(), newsDao, onItemClickListener)
        }
    }

}