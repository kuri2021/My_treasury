package com.kuri2021.Test_Project.Camera_Crop_210914


import android.content.Context
import android.graphics.Bitmap
import android.hardware.Camera
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kuri2021.Test_Project.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class PhotoFragment: Fragment() , SurfaceHolder.Callback {
    var camera: Camera? = null
    val surfaceView: SurfaceView? = null
    val surfaceHolder: SurfaceHolder? = null
    var previewing = false

    lateinit var make_photo_button:Button
    lateinit var previewLayout:LinearLayout
    lateinit var borderCamera:View
    lateinit var resBorderSizeTV:TextView

    private var mListener: OnFragmentInteractionListener?=null
    lateinit var previewSizeOptimal:Camera.Size

    open interface OnFragmentInteractionListener {
//        onFragmentInteraction(bitmap:Bitmap)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setRetainInstance(true);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
        val view=inflater.inflate(R.layout.fragment_photo,container,false)

        previewLayout=view.findViewById(R.id.preview_layout)
        borderCamera=view.findViewById(R.id.border_camera)
        resBorderSizeTV=view.findViewById(R.id.res_border_size)
        make_photo_button=view.findViewById(R.id.make_photo_button)

        surfaceView=view.findViewById(R.id.camera_preview_surface)
        surfaceHolder=surfaceView.holder
        surfaceHolder.addCallback(this)
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);



//        make_photo_button.setOnClickListener {
//            if (camera!=null){
//                camera.takePicture(myShutterCallback,
//                    myPictureCallback_RAW, myPictureCallback_JPG);
//            }
//        }


        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener=null
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        camera = Camera.open();
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        if (previewing) {
            camera?.stopPreview();
            previewing = false;
        }
        if(camera!=null){
            try{
                val parameters = camera!!.parameters
                val previewSizes = parameters.supportedPreviewSizes
                //find optimal - it very important

                //find optimal - it very important
                previewSizeOptimal = getOptimalPreviewSize(previewSizes, parameters.pictureSize.width, parameters.pictureSize.height)!!


                //set parameters
                if (previewSizeOptimal != null) {
                    parameters.setPreviewSize(previewSizeOptimal.width, previewSizeOptimal.height)
                }

                if (camera!!.parameters.focusMode.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
                    parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
                }
                if (camera!!.parameters.flashMode.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
                    parameters.flashMode = Camera.Parameters.FLASH_MODE_AUTO
                }

                camera!!.parameters = parameters

                //rotate screen, because camera sensor usually in landscape mode

                //rotate screen, because camera sensor usually in landscape mode
                val display = (requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                if (display.rotation == Surface.ROTATION_0) {
                    camera!!.setDisplayOrientation(90)
                } else if (display.rotation == Surface.ROTATION_270) {
                    camera!!.setDisplayOrientation(180)
                }

                //write some info


                //write some info
                val x1: Int = previewLayout.getWidth()
                val y1: Int = previewLayout.getHeight()

                val x2: Int = borderCamera.getWidth()
                val y2: Int = borderCamera.getHeight()

                val info = """
                    Preview width:$x1
                    Preview height:$y1
                    Border width:$x2
                    Border height:$y2
                    """.trimIndent()
                resBorderSizeTV.setText(info)

                camera!!.setPreviewDisplay(surfaceHolder)
                camera!!.startPreview()
                previewing = true

            }catch (e: IOException) {
                e.printStackTrace();
            }
        }
    }


    fun getOptimalPreviewSize(sizes: List<Camera.Size>?, w: Int, h: Int): Camera.Size? {
        val ASPECT_TOLERANCE = 0.1
        val targetRatio = w.toDouble() / h
        if (sizes == null) return null
        var optimalSize: Camera.Size? = null
        var minDiff = Double.MAX_VALUE

        // Try to find an size match aspect ratio and size
        for (size in sizes) {
            val ratio = size.width.toDouble() / size.height
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue
            if (Math.abs(size.height - h) < minDiff) {
                optimalSize = size
                minDiff = Math.abs(size.height - h).toDouble()
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE
            for (size in sizes) {
                if (Math.abs(size.height - h) < minDiff) {
                    optimalSize = size
                    minDiff = Math.abs(size.height - h).toDouble()
                }
            }
        }
        return optimalSize
    }


    override fun surfaceDestroyed(holder: SurfaceHolder) {
        camera?.stopPreview();
        camera?.release();
        camera = null;
        previewing = false;
    }

//    private fun myShutterCallback: Camera.ShutterCallback = Camera.ShutterCallback {
//    }



    fun createImageFile(bitmap: Bitmap) {
        val path: File = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        val timeStamp: String = SimpleDateFormat("MMdd_HHmmssSSS").format(Date())
        val imageFileName = "region_$timeStamp.jpg"
        val file = File(path, imageFileName)
        try {
            // Make sure the Pictures directory exists.
            if (path.mkdirs()) {
                Toast.makeText(context, "Not exist :" + path.getName(), Toast.LENGTH_SHORT).show()
            }
            val os: OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            Log.i("ExternalStorage", "Writed " + path + file.getName())
            // Tell the media scanner about the new file so that it is
            // immediately available to the user.
            MediaScannerConnection.scanFile(
                context, arrayOf(file.toString()), null
            ) { path, uri ->
                Log.i("ExternalStorage", "Scanned $path:")
                Log.i("ExternalStorage", "-> uri=$uri")
            }
            Toast.makeText(context, file.getName(), Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Unable to create file, likely because external storage is
            // not currently mounted.
            Log.w("ExternalStorage", "Error writing $file", e)
        }
    }



}