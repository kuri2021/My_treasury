package com.kuri2021.Test_Project.Camera_Crop

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_canera_crop.*
import java.io.File


class Canera_Crop : AppCompatActivity() {

    val TAKE_PICTURE = 1
    val CROP_PICTURE = 2
    private var pictureUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canera_crop)

        val builder = VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //안드로이드 버전확인
            //권한 허용이 됐는지 확인
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else { //권한 허용 요청
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            }
        }
        btn_takepicture.setOnClickListener {
            var cameraIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            val url = "tmp_" + System.currentTimeMillis().toString() + ".jpg"
            pictureUri=Uri.fromFile(File(Environment.getExternalStorageDirectory(),url))

            cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,pictureUri)
            cameraIntent.putExtra("return-data",true)
            startActivityForResult(cameraIntent,CROP_PICTURE)
        }

    }

    fun onClick(view: View) {
        when (view.getId()) {
            R.id.btn_takepicture -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                // 임시로 사용할 파일의 경로를 생성
                val url = "tmp_" + System.currentTimeMillis().toString() + ".jpg"
                pictureUri = Uri.fromFile(File(Environment.getExternalStorageDirectory(), url))
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
                cameraIntent.putExtra("return-data", true)
                startActivityForResult(cameraIntent, CROP_PICTURE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PICTURE -> if (data != null) {
                if (resultCode === RESULT_OK && data.hasExtra("data")) { //데이터를 가지고 있는지 확인
//                    val extras: Bundle? = data.getBundleExtra()
                    val extras: Bundle? =data.extras
                    if (extras != null) {
                        val photo = extras.getParcelable<Bitmap>("data") //크롭한 이미지 가져오기
                        iv_takepicture.setImageBitmap(photo) //이미지뷰에 넣기
                    }

                    // 임시 파일 삭제
                    val f = File(pictureUri!!.path)
                    if (f.exists()) f.delete()
                }
            }
            CROP_PICTURE -> {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                val intent = Intent("com.android.camera.action.CROP")
                intent.setDataAndType(pictureUri, "image/*")
                intent.putExtra("outputX", 300) //크롭한 이미지 x축 크기
                intent.putExtra("outputY", 400) //크롭한 이미지 y축 크기
                intent.putExtra("aspectX", 1) //크롭 박스의 x축 비율
                intent.putExtra("aspectY", 1) //크롭 박스의 y축 비율
                intent.putExtra("scale", true)
                intent.putExtra("return-data", true)
                startActivityForResult(intent, TAKE_PICTURE)
            }
        }
    }
}