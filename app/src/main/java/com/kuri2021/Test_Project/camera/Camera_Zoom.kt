package com.kuri2021.Test_Project.camera

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ScaleGestureDetector
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.carnera_zoom.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Camera_Zoom : AppCompatActivity() {

    private var imageCapture:ImageCapture?=null

    private lateinit var outputDirectory:File
    private lateinit var cameraExecutor:ExecutorService

//    abstract val lifecycleOwner:LifecycleOwner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carnera_zoom)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        button1.setOnClickListener { takePhoto() }

        outputDirectory=getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()


    }





    private fun takePhoto(){
        // Get a stable reference of the modifiable image capture use case
        // 수정 가능한 이미지 캡처 사용 사례의 안정적인 참조 가져오기
        val imageCapture = imageCapture ?: return
        Log.d("body_imageCapture->",""+imageCapture.toString())

        // Create time-stamped output file to hold the image
        // 이미지를 저장할 타임스탬프 출력 파일 생성
        val photoFile = File(outputDirectory, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg")
        Log.d("body_photoFile->",""+photoFile.toString())

        // Create output options object which contains file + metadata
        // 파일 + 메타데이터를 포함하는 출력 옵션 객체 생성
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        Log.d("body_outputOptions->",""+outputOptions.toString())

        // Set up image capture listener, which is triggered after photo has
        // been taken
        // 사진이 캡처된 후 트리거되는 이미지 캡처 리스너를 설정합니다.
        // 찍은
        imageCapture.takePicture(
            outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("body_exc->", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    Log.d("body_savedUri->", savedUri.toString())
                    tv_1.text=savedUri.toString()
                    val msg = "Photo capture succeeded: $savedUri"
                    iv.scaleType=ImageView.ScaleType.CENTER
                    iv.setImageURI(savedUri)
                    val drawable = iv.getDrawable()
                    tv_2.text=drawable.toString()
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d("body_msg->", msg.toString())
                }
            })
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener(Runnable {
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
                val cameraControl = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

                val cameraInfo=cameraControl.cameraInfo

//                cameraInfo.zoomState.observe(lifecycleOwner, Observer { zoomState ->
//                    // Use the zoom state to retireve information about the zoom ratio, etc
//                    val currentZoomRatio = zoomState.zoomRatio
//
//                    // ...
//                })
                val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                    override fun onScale(detector: ScaleGestureDetector): Boolean {
                        // Get the camera's current zoom ratio
                        val currentZoomRatio = cameraInfo.zoomState.value?.zoomRatio ?: 0F

                        // Get the pinch gesture's scaling factor
                        val delta = detector.scaleFactor

                        // Update the camera's zoom ratio. This is an asynchronous operation that returns
                        // a ListenableFuture, allowing you to listen to when the operation completes.
                        cameraControl.cameraControl.setZoomRatio(currentZoomRatio * delta)

                        // Return true, as the event was handled
                        return true
                    }
                }
                val scaleGestureDetector = ScaleGestureDetector(this, listener)

// Attach the pinch gesture listener to the viewfinder
                viewFinder.setOnTouchListener { _, event ->
                    scaleGestureDetector.onTouchEvent(event)
                    return@setOnTouchListener true
                }

                //줌 리스너 추
                seebar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                        cameraControl.cameraControl.setLinearZoom(progress / 100.toFloat())
                    }


                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
                })
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding faild", exc)
            }
        }, ContextCompat.getMainExecutor(this))


        imageCapture = ImageCapture.Builder().build()
    }

//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        cameraProviderFuture.addListener(Runnable {
//            // Used to bind the lifecycle of cameras to the lifecycle owner
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//
//            // Preview
//            val preview = Preview.Builder()
//                .build()
//                .also {
//                    it.setSurfaceProvider(viewFinder.surfaceProvider)
//                }
//
//            // Select back camera as a default
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            try {
//                // Unbind use cases before rebinding
//                cameraProvider.unbindAll()
//
//                // Bind use cases to camera
//                cameraProvider.bindToLifecycle(
//                    this, cameraSelector, preview)
//
//            } catch(exc: Exception) {
//                Log.e(TAG, "Use case binding failed", exc)
//            }
//
//        }, ContextCompat.getMainExecutor(this))
//    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== REQUEST_CODE_PERMISSIONS){
            if(allPermissionsGranted()){
                startCamera()
            }else{
                Toast.makeText(this, "응~안되 돌아가", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        cameraProviderFuture.addListener(Runnable {
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//
//            val preview = Preview.Builder().build().also {
//                it.setSurfaceProvider(viewFinder.surfaceProvider)
//            }
//
//            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//
//            try {
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(this, cameraSelector, preview)
//                val cameraControl = cameraProvider.bindToLifecycle(
//                    this, cameraSelector, preview, imageCapture
//                )
//
//                //줌 리스너 추
//                seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                    override fun onProgressChanged(
//                        seekBar: SeekBar?,
//                        progress: Int,
//                        fromUser: Boolean
//                    ) {
//                        cameraControl.cameraControl.setLinearZoom(progress / 100.toFloat())
//                    }
//
//                    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
//                    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
//                })
//            } catch (exc: Exception) {
//                Log.e(TAG, "Use case binding faild", exc)
//            }
//        }, ContextCompat.getMainExecutor(this))
//
//        imageCapture = ImageCapture.Builder().build()
//    }
//
}
