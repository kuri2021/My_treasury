package com.kuri2021.Test_Project.kakao_login

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.common.util.Utility.getKeyHash
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_kakao_login.*

class Kakao_Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_login)


        //키 해시값 얻어와서 Logcat 창에 출력하기 - 카카오 개발자 사이트에서 키해시값 등록해야 해서
        val keyHash = getKeyHash(this)
        Log.i("KeyHash", keyHash)

        kakao_login_btn.setOnClickListener {
            UserApiClient.instance.accessTokenInfo{tokenInfo, error ->
                if(error != null){
                    Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
                }else if(tokenInfo!=null){
                    Toast.makeText(this,"토큰 정보 보기 성공",Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,Kakao_Login_susees::class.java)
                    startActivity(intent)
                }
            }
        }
        val calllback:(OAuthToken?, Throwable?)->Unit = {token,error->
            if(error!=null){
                when{
                    error.toString()== AuthErrorCause.AccessDenied.toString()->{
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString()== AuthErrorCause.InvalidClient.toString()->{
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString()== AuthErrorCause.InvalidGrant.toString()->{
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString()== AuthErrorCause.InvalidRequest.toString()->{

                    }
                }
            }
        }
    }
}