package com.kuri2021.Test_Project.Custom_Dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.kuri2021.Test_Project.Arrays.Arrays_example
import com.kuri2021.Test_Project.Custom_Dialog.model.Custom_Dialog_Interface
import com.kuri2021.Test_Project.MainActivity
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_main.*

class Custom_Dialog : AppCompatActivity(), Custom_Dialog_Interface {

    lateinit var shared_logout: SharedPreferences
    lateinit var context: FragmentActivity

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    //    기본 얼럿 1
    override fun alert_dialog_1(
        dialog: Dialog,
        title: String,
        content: String,
        btn_text: String,
        status: String?
    ): Dialog {
        dialog.setContentView(R.layout.alert_dialog_1)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.findViewById<TextView>(R.id.alert_dialog1_title).setText(title)
        dialog.findViewById<TextView>(R.id.alert_dialog1_content).setText(content)
        dialog.findViewById<Button>(R.id.alert_dialog1_btn).setText(btn_text)
        if (status.equals("")) {
            dialog.findViewById<Button>(R.id.alert_dialog1_btn).setOnClickListener {
                dialog.dismiss()
            }
        }

        if (status.equals("test")) {
            dialog.findViewById<Button>(R.id.alert_dialog1_btn).setOnClickListener {
                custom_dialog_tv.setText(Arrays_example.array[1])
                dialog.dismiss()
            }
        }
        dialog.show()

        return dialog
    }

}