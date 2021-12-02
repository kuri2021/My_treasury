package com.kuri2021.Test_Project.Camera_Crop_210914


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.kuri2021.Test_Project.R


class Camera_Crop_210914 : AppCompatActivity() {

    var PERMISSION_ALL = 1
    var flagPermissions = false

    var PERMISSIONS = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_crop210914)
        checkPermissions();
    }
    fun onClickScanButton() {
        // check permissions
        if (!flagPermissions) {
            checkPermissions()
            return
        }
        //start photo fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.res_photo_layout, PhotoFragment())
            .addToBackStack(null)
            .commit()
    }
    fun checkPermissions() {
        if (!hasPermissions(this, *PERMISSIONS)) {
            requestPermissions(
                PERMISSIONS,
                PERMISSION_ALL
            )
            flagPermissions = false
        }
        flagPermissions = true
    }
    fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission.toString()) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true
    }
//    onFragmentIn
//    fun onFragmentInteraction(bitmap: Bitmap?) {
//        if (bitmap != null) {
//            val imageFragment = ImageFragment()
//            imageFragment.imageSetupFragment(bitmap)
//            supportFragmentManager
//                .beginTransaction()
//                .replace(R.id.res_photo_layout, imageFragment)
//                .addToBackStack(null)
//                .commit()
//        }
//    }
}