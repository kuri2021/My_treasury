package com.kuri2021.Test_Project.Arrays

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_arrays_example.*
import java.util.*

class Arrays_example : AppCompatActivity() {

    var array= arrayOf("aostest1","aostest2","aostest5","aostest21","aostest221")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrays_example)

        var btn1: Button =findViewById(R.id.btn1)
        var btn2: Button =findViewById(R.id.btn2)
        var btn3: Button =findViewById(R.id.btn3)

        btn2.setOnClickListener {
            Arrays.sort(array)
            tv2.setText(array[0]+"/"+array[1]+"/"+array[2]+"/"+array[3]+"/"+array[4]+"/")
        }
    }
}