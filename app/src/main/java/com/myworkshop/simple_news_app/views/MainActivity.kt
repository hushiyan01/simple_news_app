package com.myworkshop.simple_news_app.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.myworkshop.simple_news_app.R
import com.myworkshop.simple_news_app.databinding.ActivityMainBinding
import com.myworkshop.simple_news_app.model.VolleyImageCaching
import com.myworkshop.simple_news_app.views.adapters.OnItemClickListener

class MainActivity : AppCompatActivity() , OnItemClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()

    }

    private fun initViews() {
        VolleyImageCaching.initialize(this)
        makeTransaction(HOME_FRAG, HomeFragment(this@MainActivity))
        binding.bottomBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_home -> makeTransaction(HOME_FRAG, HomeFragment(this@MainActivity))
                R.id.bottom_favorites -> makeTransaction(FAVORITES_FRAG, FavoritesNewsFragment(this))
                R.id.bottom_account -> makeTransaction(ACCOUNT_FRAG, AccountFragment())
            }
            true
        }
    }

    private fun makeTransaction(flag: String, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fg_child_container, fragment)
            .addToBackStack(flag)
            .commit()
    }

    companion object{
        const val HOME_FRAG = "home"
        const val FAVORITES_FRAG = "favorites"
        const val ACCOUNT_FRAG = "account"
    }

    override fun onItemClick(fragment: NewsDetailFragment) {
        makeTransaction("test", fragment)
    }


}