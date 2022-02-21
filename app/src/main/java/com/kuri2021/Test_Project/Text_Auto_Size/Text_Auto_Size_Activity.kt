package com.kuri2021.Test_Project.Text_Auto_Size

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kuri2021.Test_Project.R
import android.view.Display

import android.app.Activity
import android.graphics.Point
import kotlinx.android.synthetic.main.activity_text_auto_size.*


class Text_Auto_Size_Activity : AppCompatActivity() {
    var standardSize_X:Float = 0F
    var standardSize_Y:Float = 0F
    var density = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_auto_size)


        text_auto_size_btn.setOnClickListener {
            getStandardSize()

//            text_auto_size.setTextSize(standardSize_X/3)
//            text_auto_size.setTextSize(standardSize_Y/10)
            text_auto_size_x.text= text_auto_size.textSize.toString()+"/"+standardSize_X.toString()
            text_auto_size_y.text=standardSize_Y.toString()
//            text_auto_size.setTextSize()
        }

    }
    fun getScreenSize(activity: Activity): Point? {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size
    }
    fun getStandardSize() {
        val ScreenSize = getScreenSize(this)
        density = resources.displayMetrics.density
        standardSize_X = (ScreenSize!!.x / density)
        standardSize_Y = (ScreenSize.y / density)
    }
}