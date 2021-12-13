package com.kuri2021.Test_Project.Retrofit_test

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Retrofit_Class:Retrofit_Interface {

    var retrofit:Retrofit?=null

//    예전 정석 코드
    override fun legacy_retrofit(access_token:String?): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10,TimeUnit.SECONDS)//연결시간타임
            .readTimeout(10,TimeUnit.SECONDS)//읽는 시간 타임
            .writeTimeout(10,TimeUnit.SECONDS)//쓰는 시간 타임

        httpClient.addInterceptor(object :Interceptor{
            override fun intercept(chain: Interceptor.Chain): Response {
                var request:Request
                if(access_token!=""){
                   request =chain.request().newBuilder().addHeader("PleasyPrivatKey", "PleasyDevGroup").addHeader("PleasyPhoneGroup", "Android").addHeader("AccessToken", access_token).build()
                }else{
                    request=chain.request().newBuilder().addHeader("PleasyPrivatKey", "PleasyDevGroup").addHeader("PleasyPhoneGroup", "Android").build()
                }
                return chain.proceed(request)
            }
        })
        val gson:Gson=GsonBuilder()
            .setLenient()
            .create()

        retrofit=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://simwanwoo.pw")
            .client(httpClient.build())
            .build()

        return retrofit as Retrofit
    }
//    줄수를 줄인 코드
    override fun Modern_retrofit(access_token:String?): Retrofit {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10,TimeUnit.SECONDS)//연결시간타임
            .readTimeout(10,TimeUnit.SECONDS)//읽는 시간 타임
            .writeTimeout(10,TimeUnit.SECONDS)//쓰는 시간 타임

//    레트로핏 헤더 추가버전
        httpClient.addInterceptor{ chain ->
            val request:Request
            if(access_token != null) {
                request =
                    chain.request().newBuilder().addHeader("PleasyPrivatKey", "PleasyDevGroup").addHeader("PleasyPhoneGroup", "Android").addHeader("AccessToken", access_token).build()
            }else {
                request =
                    chain.request().newBuilder().addHeader("PleasyPrivatKey", "PleasyDevGroup").addHeader("PleasyPhoneGroup", "Android").build()
            }

            chain.proceed(request)
        }
        val gson:Gson=GsonBuilder()
            .setLenient()
            .create()

        retrofit=Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://simwanwoo.pw")
            .client(httpClient.build())
            .build()

        return retrofit as Retrofit
    }

}