package com.kuri2021.Test_Project

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kuri2021.Test_Project.Arrays.Arrays_example
import com.kuri2021.Test_Project.Camera_Crop.Canera_Crop
import com.kuri2021.Test_Project.Camera_Crop_210914.Camera_Crop_210914
import com.kuri2021.Test_Project.Camera_Crop_210915.croperino
import com.kuri2021.Test_Project.Camera_FlashLight.Camera_FlashLight
import com.kuri2021.Test_Project.CoordinatorLayout.CoordinatorLayout_Activity
import com.kuri2021.Test_Project.Custom_Dialog.Custom_Dialog
import com.kuri2021.Test_Project.Fragment_in_ViewPager_22_01_12.Fragment_in_ViewPager_Main
import com.kuri2021.Test_Project.Gamail.Gmail_Test
import com.kuri2021.Test_Project.Kakao_Map.Kakao_Map
import com.kuri2021.Test_Project.Text_Auto_Size.Text_Auto_Size_Activity
import com.kuri2021.Test_Project.Toast.Toast_Test
import com.kuri2021.Test_Project.camera.Camera_Zoom
import com.kuri2021.Test_Project.crop_211019.crop_211019
//import com.kuri2021.Test_Project.iBeacon.DeviceScanActivity
import com.kuri2021.Test_Project.kakao_login.Kakao_Login
import com.kuri2021.Test_Project.permision_21_12_20.Permision
import com.kuri2021.Test_Project.permision_21_12_20.User_Info1
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    var test="확인"


    lateinit var permssionCheck: Any
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        User_Info1.text_1="이거지"

        val PERMISSION_REQUEST_CAMERA = 123

        var btn1: Button = findViewById(R.id.btn1)
        var btn2: Button = findViewById(R.id.btn2)
        var btn3: Button = findViewById(R.id.btn3)
        var btn4: Button = findViewById(R.id.btn4)
        var btn5:Button=findViewById(R.id.btn5)
        var btn6:Button=findViewById(R.id.btn6)
        var btn7:Button=findViewById(R.id.btn7)
        var btn8=findViewById<Button>(R.id.btn8)

        btn1.setOnClickListener {
            var intent = Intent(this, Arrays_example::class.java)
            startActivity(intent)
        }

        btn2.setOnClickListener {
            var intent = Intent(this, Camera_Zoom::class.java)
            startActivity(intent)
        }

        btn3.setOnClickListener {
            var intent = Intent(this, Toast_Test::class.java)
            startActivity(intent)
        }
        btn4.setOnClickListener {
            val dialog = Dialog(this)
            Custom_Dialog().alert_dialog_1(
                dialog,
                "테스트입니다.",
                "return값은 Arrays_example().array[0]입니다.",
                "확인",
                "test",
            )
        }
        btn5.setOnClickListener {
            var intent=Intent(this,Canera_Crop::class.java)
            startActivity(intent)
        }
        btn6.setOnClickListener {
            var intent=Intent(this,Camera_Crop_210914::class.java)
            startActivity(intent)
        }
        btn7.setOnClickListener {
            var intent=Intent(this,croperino::class.java)
            startActivity(intent)
        }
        btn8.setOnClickListener {
            var intent=Intent(this,crop_211019::class.java)
            startActivity(intent)
        }
        btn9.setOnClickListener {
            var intent=Intent(this,Gmail_Test::class.java)
            startActivity(intent)
        }
        btn10.setOnClickListener {
            var intent=Intent(this, Camera_FlashLight::class.java)
            startActivity(intent)
        }
        btn11.setOnClickListener {
            var intent=Intent(this, CoordinatorLayout_Activity::class.java)
            startActivity(intent)
        }
        btn12.setOnClickListener {
            var intent=Intent(this,Kakao_Login::class.java)
            startActivity(intent)
        }
        btn13.setOnClickListener {
            var intent=Intent(this,Kakao_Map::class.java)
            startActivity(intent)
        }
        btn14.setOnClickListener {
            var intent=Intent(this,Permision::class.java)
            startActivity(intent)
        }
        btn15.setOnClickListener {
           var intent=Intent(this,Fragment_in_ViewPager_Main::class.java)
            startActivity(intent)
        }
        btn16.setOnClickListener {
            var intent=Intent(this,Text_Auto_Size_Activity::class.java)
            startActivity(intent)
        }
    }
}