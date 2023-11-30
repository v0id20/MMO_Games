package com.example.mmogames.screenshotviewer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.mmogames.R
import com.squareup.picasso.Picasso


class ScreenshotPagerAdapter(var context: Context, var itemList: ArrayList<String>) : PagerAdapter() {
    override fun getCount(): Int = itemList.size


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view== `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.full_screen_picture, container, false) as ViewGroup
        val imageView = view.findViewById<ImageView>(R.id.picture)
        Picasso.get().load(itemList.get(position)).into(imageView)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView((`object` as View))
    }
}