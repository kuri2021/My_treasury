package com.kuri2021.Test_Project.Toast

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity.FILL_HORIZONTAL
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kuri2021.Test_Project.R

class Custom_Toast : AppCompatActivity(),Custom_Toast_Interface{

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
    override fun Toast(content:String,context: Context){
        val inflater=LayoutInflater.from(context)
        var view1=inflater.inflate(R.layout.custom_toast,null)
        var toast_message:TextView=view1.findViewById(R.id.toast_tv)
        toast_message.setText(content)

        var toast=Toast(context)
        toast.setGravity(FILL_HORIZONTAL, 0, 0)//양쪽 기본 여백 없얘는 방법
        toast.view=view1
        toast.show()
    }
}