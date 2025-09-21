package com.example.remind

import android.os.Handler
import android.os.Looper

class MemorySlideshow(
    private val images: List<String>, // local resource IDs as strings
    private val intervalMillis: Long = 5000L,
    private val onImageChange: (String) -> Unit
) {
    private var currentIndex = 0
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            if (images.isNotEmpty()) {
                onImageChange(images[currentIndex])
                currentIndex = (currentIndex + 1) % images.size
                handler.postDelayed(this, intervalMillis)
            }
        }
    }

    fun start() {
        if (images.isNotEmpty()) {
            handler.post(runnable)
        }
    }

    fun stop() {
        handler.removeCallbacks(runnable)
    }

    fun reset() {
        stop()
        currentIndex = 0
        start()
    }
}