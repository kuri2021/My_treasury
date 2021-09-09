package com.kuri2021.Test_Project

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kuri2021.Test_Project.Arrays.Arrays_example
import com.kuri2021.Test_Project.Toast.Toast_Test
import com.kuri2021.Test_Project.camera.Camera_Zoom
import java.util.*


class MainActivity : AppCompatActivity() {



    private lateinit var permssionCheck:Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val PERMISSION_REQUEST_CAMERA = 123

        var btn1:Button=findViewById(R.id.btn1)
        var btn2:Button=findViewById(R.id.btn2)
        var btn3:Button=findViewById(R.id.btn3)

        btn1.setOnClickListener {
            var intent= Intent(this,Arrays_example::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            var intent= Intent(this,Camera_Zoom::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            var intent= Intent(this,Toast_Test::class.java)
            startActivity(intent)
        }


    }
}