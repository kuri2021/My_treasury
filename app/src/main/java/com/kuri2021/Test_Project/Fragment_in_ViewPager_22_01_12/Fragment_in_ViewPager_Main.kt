package com.kuri2021.Test_Project.Fragment_in_ViewPager_22_01_12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kuri2021.Test_Project.R
import java.util.ArrayList

class Fragment_in_ViewPager_Main : AppCompatActivity() {

//    var fragment1=Fragment1

    var imageList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_in_view_pager_main)


        var fragment1=Fragment1()
        val transaction=supportFragmentManager.beginTransaction().add(R.id.frag,fragment1)
        transaction.commit()
    }
}