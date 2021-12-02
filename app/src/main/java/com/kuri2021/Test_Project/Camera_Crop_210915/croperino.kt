package com.kuri2021.Test_Project.Camera_Crop_210915

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kuri2021.Test_Project.R

class croperino : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_croperino)

        val intent = Intent(Intent.ACTION_PICK)
        startActivity(intent)
    }
}