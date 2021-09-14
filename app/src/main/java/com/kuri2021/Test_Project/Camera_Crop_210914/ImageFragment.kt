package com.kuri2021.Test_Project.Camera_Crop_210914

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.fragment_image.view.*


class ImageFragment: Fragment() {
    private var bitmap: Bitmap? = null


    open fun imageSetupFragment(bitmap: Bitmap?) {
        if (bitmap != null) {
            this.bitmap = bitmap
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setRetainInstance(true);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_image,container,false)
        if (bitmap != null) {
            view.res_photo.setImageBitmap(bitmap)
            val info = """
                image with:${bitmap!!.width}
                image height:${bitmap!!.height}
                """.trimIndent()
            view.res_photo_size.setText(info)
        }

        return view
    }


}