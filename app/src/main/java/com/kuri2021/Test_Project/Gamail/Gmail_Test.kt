package com.kuri2021.Test_Project.Gamail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_gmail_test.*

class Gmail_Test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gmail_test)

        gmail_btn.setOnClickListener {
            openUrl("admin@pleasy.co.kr")
        }
    }
    private fun openUrl(url: String) {
        //val uri=Uri.parse("admin@pleasy.co.kr")
        var intent=Intent(Intent.ACTION_SENDTO)
//
//            intent.type = "text/plain"
//            intent.putExtra(Intent.EXTRA_EMAIL,"admin@pleasy.co.kr")
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mailto:"))
        var address=ArrayList<String>()
//            address.add("")
        intent.putExtra(Intent.EXTRA_EMAIL, "pleasy.helpcustomer@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT,"주차스티커가 안 왔어요.")
        intent.putExtra(Intent.EXTRA_TEXT,"메일 내용 :\n" +
                "\n" +
                "\n" +
                "\n" +
                "====== 아래 정보를 지우지 말고 메일을 보내주세요 ======\n" +
                "\n" +
                "[버전정보]\n" +
                "OS: Android xx.x\n" +

                "\n" +
                "[신청정보]\n" +

                "-신청내역\n" +

                "\n" +
                "\n" +
                "플리지는 문의 사항 처리를 위해 문의에 포함된 개인정보를 수집 및 이용할 수 있습니다. 이러한 정보는 답변을 위한 목적으로만 사용됩니다.\n" +
                "\n" +
                "귀하께서는 위와 같이 수집하는 개인정보에 대해 동의하지 않거나, 개인정보를 기재하지 않음으로써 거부할 수 있습니다. 다만, 이때 문의 처리 및 결과 회신이 제한됩니다.")
        try {
            startActivity(intent);
        } catch (e: ActivityNotFoundException) {
//            var intent2=Intent(this,Gmail_Fail_Activity::class.java)
//            startActivity(intent2)
            Log.d("body","fail")

        }
    }

}