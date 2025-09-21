package com.example.remind

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class MemorySlideshowFragment : Fragment() {

    private lateinit var slideshowImageView: ImageView
    private var images = listOf(
        R.drawable.avatar_1,
        R.drawable.avatar_2,
        R.drawable.avatar_3
    )
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private val interval: Long = 3000 // 3 seconds

    private val slideshowRunnable = object : Runnable {
        override fun run() {
            if (images.isNotEmpty()) {
                Glide.with(requireContext()).load(images[currentIndex]).into(slideshowImageView)
                currentIndex = (currentIndex + 1) % images.size
                handler.postDelayed(this, interval)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_slideshow, container, false)
        slideshowImageView = view.findViewById(R.id.slideshowImageView)
        return view
    }

    override fun onResume() {
        super.onResume()
        handler.post(slideshowRunnable)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(slideshowRunnable)
    }
}
