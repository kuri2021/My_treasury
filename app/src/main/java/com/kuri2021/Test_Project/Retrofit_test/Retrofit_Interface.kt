package com.kuri2021.Test_Project.Retrofit_test

import retrofit2.Retrofit

interface Retrofit_Interface {
    fun legacy_retrofit(access_token:String?): Retrofit
    fun Modern_retrofit(access_token:String?): Retrofit
}