package com.myworkshop.simple_news_app.views

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.myworkshop.simple_news_app.databinding.FragmentNewsDetailBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


class NewsDetailFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("title")
        val authors = arguments?.getString("authors")
        val body = arguments?.getString("body")
        val imageUrl = arguments?.getString("image_url")
        binding.tvTitle.text = title
        binding.tvAuthorName.text = authors
        binding.tvBody.text = body
        Picasso.get()
            .load(imageUrl)
            .into(object :Target{
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    binding.upperPortion.background = BitmapDrawable(requireContext().resources, bitmap)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                }
            })

        binding.btnShare.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, binding.tvTitle.text.toString())
            sharingIntent.putExtra(Intent.EXTRA_TEXT, binding.tvBody.text.toString())
            val chooserIntent = Intent.createChooser(sharingIntent, "Share using")
            startActivity(chooserIntent)
        }
    }
}