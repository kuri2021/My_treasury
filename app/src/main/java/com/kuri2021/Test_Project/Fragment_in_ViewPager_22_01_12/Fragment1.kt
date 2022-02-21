package com.kuri2021.Test_Project.Fragment_in_ViewPager_22_01_12

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.kuri2021.Test_Project.Fragment_in_ViewPager_22_01_12.adapter.ViewPagerAdapter
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.fragment_in_viewpager_fragment1.view.*
import java.util.*

class Fragment1:Fragment() {

    var currentPage = 0
    var timer: Timer? = null
    val DELAY_MS: Long = 500 //초반딜레이

    val PERIOD_MS: Long = 3000 // 다음에서까지 넘어갈 딜레이


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view=inflater.inflate(R.layout.fragment_in_viewpager_fragment1,container,false)


        var imageList = ArrayList<Int>()
        imageList.add(R.drawable.images0)
        imageList.add(R.drawable.images1)
        imageList.add(R.drawable.images3)
        imageList.add(R.drawable.images4)

        var adapter = ViewPagerAdapter(requireContext(), imageList)
        view.vp.adapter=adapter

        val handler = Handler()
        val Update = Runnable {
            if (currentPage == 3) {
                currentPage = 0
            }
            view.vp.setCurrentItem(currentPage++, true)
        }

        timer = Timer()
        timer!!.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, DELAY_MS, PERIOD_MS)


        return view
    }

}