package com.kuri2021.Test_Project.camera

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_camera_next.*

class camera_next : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_next)

        var bitmap: String? =intent.getStringExtra("bitmap")
//        iv2.setImageBitmap(bitmap)
    }
}