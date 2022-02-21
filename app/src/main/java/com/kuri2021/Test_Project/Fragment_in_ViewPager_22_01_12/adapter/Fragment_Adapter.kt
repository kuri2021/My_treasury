package com.kuri2021.Test_Project.Fragment_in_ViewPager_22_01_12.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class Fragment_Adapter (supportFragmentManager: FragmentManager): FragmentPagerAdapter(supportFragmentManager) {
    var list=ArrayList<String>()
    override fun getCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(position: Int): Fragment {
        TODO("Not yet implemented")
    }
}