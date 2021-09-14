package com.kuri2021.Test_Project.Custom_Dialog.model

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity

interface Custom_Dialog_Interface {

    fun alert_dialog_1(dialog: Dialog, title: String, content: String,  btn_text: String, status:String?): Dialog
//    fun dialog_timer_excess(dialog: Dialog):Dialog
}