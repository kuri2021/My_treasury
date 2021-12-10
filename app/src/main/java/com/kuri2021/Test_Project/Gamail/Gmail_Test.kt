package com.kuri2021.Test_Project.Gamail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_gmail_test.*
import java.text.SimpleDateFormat
import java.util.*

class Gmail_Test : AppCompatActivity() {

    var name:String?=null
    var Processing_phone:String?=null
    lateinit var time:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gmail_test)

        gmail_btn.setOnClickListener {
            time=getTimeYYYYMMDD()//시간을 계산해주는 메소드

//            Gmail에 받는 사람과 제목 메일 내용까지 자동으로 적힘
            var uriText:String = "mailto:pleasy.helpcustomer@gmail.com" + "?subject=" + Uri.encode("주차스티커가 안 왔어요.") + "&body=" + Uri.encode(
                "메일 내용 :\n" +
                        "\n" +
                        "\n" +
                        "\n" +
                        "====== 아래 정보를 지우지 말고 메일을 보내주세요 ======\n" +
                        "\n" +
                        "[버전정보]\n" +
                        "OS: \n" +
                        "앱: \n" +
                        "\n" +
                        "[신청정보]\n" +
                        "-사용자 아이디 : \n" +
                        "-신청내역\n" +
                        "\t신청일자 : \n" +
                        "\t이름 : \n" +
                        "\t연락처 : \n" +
                        "\t주소 : \n" +
                        "\n" +
                        "\n" +
                        "플리지는 문의 사항 처리를 위해 문의에 포함된 개인정보를 수집 및 이용할 수 있습니다. 이러한 정보는 답변을 위한 목적으로만 사용됩니다.\n" +
                        "\n" +
                        "귀하께서는 위와 같이 수집하는 개인정보에 대해 동의하지 않거나, 개인정보를 기재하지 않음으로써 거부할 수 있습니다. 다만, 이때 문의 처리 및 결과 회신이 제한됩니다.")
            var uri:Uri=Uri.parse(uriText)
            var sendIntent:Intent =Intent(Intent.ACTION_SENDTO);
            sendIntent.setData(uri);
            try {
                startActivity(Intent.createChooser(sendIntent, "Send email"));
            } catch (e: Exception) {
                Log.d("body",e.toString())
            }
        }
    }
    fun getTimeYYYYMMDD(): String {
        //현재시간
        val curTime = Date().time
        val format = SimpleDateFormat("yyyy-MM-dd")
        //TimeZone  설정 (GMT +9)
        format.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        //결과물
        return format.format(curTime)
    }
}