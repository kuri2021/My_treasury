package com.kuri2021.Test_Project.Fragment_in_ViewPager_22_01_12.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.kuri2021.Test_Project.R
import java.util.ArrayList

class ViewPagerAdapter(var context: Context, var imageList: ArrayList<Int>) :
    PagerAdapter() {
    override fun getCount(): Int {
        return imageList.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val page: View = inflater.inflate(R.layout.page, null)
        val iv = page.findViewById<ImageView>(R.id.iv)
        iv.setImageResource(imageList[position])
        container.addView(page)
        return page
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }
}
