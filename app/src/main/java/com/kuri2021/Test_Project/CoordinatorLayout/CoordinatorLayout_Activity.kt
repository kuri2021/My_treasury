package com.kuri2021.Test_Project.CoordinatorLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_coordinator_layout.*

class CoordinatorLayout_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)

        setSupportActionBar(app_toolbar)
        val dataSet=(1..50).toList()
        bible_recycler_view.adapter=SampleRecyclerAdapter(dataSet)
    }
}