package com.kuri2021.Test_Project.Toast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_toast_test.*

class Toast_Test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast_test)

        toast_btn.setOnClickListener {
            Custom_Toast().Toast("테스트 하는 중",this)
        }
    }
}