package com.kuri2021.Test_Project.kakao_login

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this,"abb3b1ea82d3d781b1b8b788b984cfdf")
    }
}