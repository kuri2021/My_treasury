package com.kuri2021.Test_Project.crop_211019

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kuri2021.Test_Project.R
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import java.io.File


class crop_211019 : AppCompatActivity() {

    private val PICK_FROM_CAMERA = 0
    private val PICK_FROM_ALBUM = 1
    private val CROP_FROM_CAMERA = 2

    private var mImageCaptureUri: Uri? = null
    private var mPhotoImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop211019)

        mPhotoImageView=findViewById(R.id.mPhotoImageView)

        mPhotoImageView!!.setOnClickListener {
            storege()
        }

    }

    fun storege(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode!= RESULT_OK){
            return
        }
        when(requestCode){
            CROP_FROM_CAMERA -> {
                val extras: Bundle? = data?.extras
                if(extras!=null){
                    val photo: Bitmap? = extras.getParcelable("data")
                    mPhotoImageView?.setImageBitmap(photo)
                }
                var f=File(mImageCaptureUri?.path)
                if(f.exists()){
                    f.delete()
                }
            }
            PICK_FROM_ALBUM->{
                if (data != null) {
                    mImageCaptureUri = data.getData()
                }
            }
            PICK_FROM_CAMERA->{
                val intent = Intent("com.android.camera.action.CROP")
                intent.setDataAndType(mImageCaptureUri, "image/*")

                intent.putExtra("outputX", 90)
                intent.putExtra("outputY", 90)
                intent.putExtra("aspectX", 1)
                intent.putExtra("aspectY", 1)
                intent.putExtra("scale", true)
                intent.putExtra("return-data", true)
                startActivityForResult(intent, CROP_FROM_CAMERA)
            }
        }

    }
}