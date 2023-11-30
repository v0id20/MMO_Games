package com.example.mmogames.screenshotviewer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.mmogames.R
import com.example.mmogames.gameinfo.SCREENSHOT_ARRAY_EXTRA

class ScreenshotViewerActivity : AppCompatActivity() {
    lateinit var screenshotArray: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("ScreenshotViewerActivity", "oncreate 1")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screenshot_viewer)
        screenshotArray =
            intent.getStringArrayListExtra(SCREENSHOT_ARRAY_EXTRA) ?: ArrayList<String>()
        Log.i("ScreenshotViewerActivity", "oncreate2")
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        if (screenshotArray.size!=0) {
            Log.i("ScreenshotViewerActivity", "screenshotArray not null")
            viewPager.adapter = ScreenshotPagerAdapter(this, screenshotArray)
        } else {
            Log.i("ScreenshotViewerActivity", "screenshotArray is null")
        }
    }
}