package com.myworkshop.simple_news_app.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.myworkshop.simple_news_app.databinding.FragmentHomeBinding
import com.myworkshop.simple_news_app.model.db.NewsDBHelper
import com.myworkshop.simple_news_app.model.db.NewsDao
import com.myworkshop.simple_news_app.model.News
import com.myworkshop.simple_news_app.model.NewsResponse
import com.myworkshop.simple_news_app.model.VolleyHandler
import com.myworkshop.simple_news_app.presenter.MVPNews
import com.myworkshop.simple_news_app.presenter.NewsPresenter
import com.myworkshop.simple_news_app.views.adapters.NewsAdapter
import com.myworkshop.simple_news_app.views.adapters.OnItemClickListener

class HomeFragment(val onItemClickListener: OnItemClickListener) : Fragment(), MVPNews.NewsView{
    lateinit var newsDao: NewsDao
    lateinit var binding:FragmentHomeBinding
    private lateinit var presenter: NewsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsDao = NewsDao(NewsDBHelper(requireContext()))
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        presenter = NewsPresenter(VolleyHandler(requireContext()), this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getNews()
        binding.apply {
            btnSearch.setOnClickListener {
                val keywords = etSearchKeyWord.text
                if(keywords.isNotEmpty()){
                    presenter.getNewsByKeywords(keywords.toString())
                    binding.rvLatestNews.adapter?.notifyDataSetChanged()
                }else{
                    presenter.getNews()
                }
            }
        }
//        loadNewsData()
    }


//    private fun loadNewsData() {
//        val requestQueue = Volley.newRequestQueue(requireContext())
//
//        val stringRequest = object : StringRequest(
//            Method.GET,
//            "https://api.currentsapi.services/v1/latest-news",
//            {
//                //success block
//                val typeToken = object : TypeToken<NewsResponse>(){}
//                val response = Gson().fromJson(it, typeToken)
//                if(response.status == "ok"){
//                    binding.rvLatestNews.apply {
//                        layoutManager = LinearLayoutManager(requireContext())
//                        adapter = NewsAdapter(
//                            response.news.map { item -> News(0L, newsDao.isFavorite(item.id), item) },
//                            requireContext(),
//                            newsDao,
//                            onItemClickListener)
//                    }
//                }else{
//                    Toast.makeText(requireContext(), "fetching data failed", Toast.LENGTH_SHORT).show()
//                }
//
//            }, {
//
//                Log.i("tag", "failed: ${it.toString()}")
//            }
//        ){
//            override fun getHeaders(): MutableMap<String, String> {
//                val header = HashMap<String,String>()
//                header["Authorization"] = "RX4bm69aClM6SBhDEqQ6lHhiVktco-OP1e-C2bgb-GpgmJSK"
//                return header
//            }
//        }
//
//        requestQueue.add(stringRequest)
//
//    }

    override fun setSuccess(newsResponse: NewsResponse) {
        binding.rvLatestNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = NewsAdapter(
                newsResponse.news.map { item -> News(0L, newsDao.isFavorite(item.id), item) },
                requireContext(),
                newsDao,
                onItemClickListener
            )
        }
    }

    override fun setFailure(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }


}