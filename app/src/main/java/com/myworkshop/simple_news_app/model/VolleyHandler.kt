package com.myworkshop.simple_news_app.model

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class VolleyHandler(private val context: Context) {

    fun fetchNewsResponse(responseCallback: ResponseCallBack) {
        val requestQueue = Volley.newRequestQueue(context)

        val stringRequest = object : StringRequest(
            Request.Method.GET,
            BASE_URL_GET_ALL_NEWS,
            {
                val typeToken = object : TypeToken<NewsResponse>() {}
                val response = Gson().fromJson(it, typeToken)
                responseCallback.success(response)
            },
            {
                responseCallback.failure(it.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                return getHeader()
            }
        }
        requestQueue.add(stringRequest)
    }

    fun fetchNewsResponseByKeyWords(keywords:String, responseCallback: ResponseCallBack){
        val requestQueue = Volley.newRequestQueue(context)
        val params = "keywords=$keywords"
        val queryUrl = BASE_URL_GET_NEWS_BY_KEYWORD+params
        val stringRequest = object :StringRequest(
            Method.GET,
            queryUrl,
            {
                val typeToken = object :TypeToken<NewsResponse>(){}
                val response = Gson().fromJson(it, typeToken)
                responseCallback.success(response)
            },
            {
                responseCallback.failure(it.toString())
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                return getHeader()
            }
        }
        requestQueue.add(stringRequest)
    }

    companion object{
        const val BASE_URL_GET_ALL_NEWS = "https://api.currentsapi.services/v1/latest-news"
        const val BASE_URL_GET_NEWS_BY_KEYWORD = "https://api.currentsapi.services/v1/search?"
        fun getHeader():MutableMap<String, String>{
            val header = HashMap<String, String>()
            header["Authorization"] = "RX4bm69aClM6SBhDEqQ6lHhiVktco-OP1e-C2bgb-GpgmJSK"
            return header
        }
    }


}