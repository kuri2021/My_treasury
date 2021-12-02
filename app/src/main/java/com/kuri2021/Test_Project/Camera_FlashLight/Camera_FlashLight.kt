package com.kuri2021.Test_Project.Camera_FlashLight

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.camera.core.*
import android.view.Menu
import android.view.ScaleGestureDetector
import android.view.View
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraControl
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import com.kuri2021.Test_Project.R
import kotlinx.android.synthetic.main.activity_camera_flash_light.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.camera.core.impl.utils.Threads;

class Camera_FlashLight : AppCompatActivity() {

//    lateinit private var binding : ActivityMainBinding
    private var preview : Preview? = null
    private var imageCapture: ImageCapture? = null
    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private var camera : Camera? = null
    private var cameraController : CameraControl? = null
    private var cameraInfo: CameraInfo? = null
    var cameraflashmode:Int = 0

    var flag=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_flash_light)


        startCamera()
        btnTakePicture.setOnClickListener {
            takePhoto()
        }
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()

        btnTorch.setOnClickListener {
            when (cameraInfo?.torchState?.value) {
                TorchState.ON -> {
                    cameraController?.enableTorch(false)
                    btnTorch.text = "Torch ON"
                }
                TorchState.OFF -> {
                    cameraController?.enableTorch(true)
                    btnTorch.text = "Torch OFF"
                }
            }
        }

        btn1X.setOnClickListener {
            cameraController?.setZoomRatio(1F)
        }
        btn2X.setOnClickListener {
            cameraController?.setZoomRatio(2F)
        }
        btn5X.setOnClickListener {
            cameraController?.setZoomRatio(5F)
        }
        btn10X.setOnClickListener {
            cameraController?.setZoomRatio(10F)
        }






    }
    private fun takePhoto() {
// Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return
// Create time-stamped output file to hold the image
        val photoFile = File(
            outputDirectory,
            newJpgFileName())
// Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
// Set up image capture listener, which is triggered after photo has
// been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.d("CameraX-Debug", "Photo capture failed: ${exc.message}", exc)
                }
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val msg = "Photo capture succeeded: $savedUri"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d("CameraX-Debug", msg)
                    Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also{
                        it.data = savedUri
                        sendBroadcast(it)
                    }
                }
            })
    }
    // viewFinder 설정 : Preview
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
// Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
// Preview
            preview = Preview.Builder()
                .build()
                .also { it.setSurfaceProvider(viewFinder.surfaceProvider) }
// ImageCapture
            imageCapture = ImageCapture.Builder()
                .build()
// Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            try {
// Unbind use cases before rebinding
                cameraProvider.unbindAll()
// Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture)
                cameraController = camera!!.cameraControl
                cameraInfo = camera!!.cameraInfo

                cameraInfo!!.zoomState.observe(this, androidx.lifecycle.Observer {
                    val currentZoomRatio = it.zoomRatio
                    Log.d("MyCameraXBasic", currentZoomRatio.toString())
                    txtZoomState.text = currentZoomRatio.toString()
                    txtMaxZoom.text = it.maxZoomRatio.toString()
                    txtMinZoom.text = it.minZoomRatio.toString()
                })



            } catch(exc: Exception) {
                Log.d("CameraX-Debug", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))

        val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                // Get the camera's current zoom ratio
                val currentZoomRatio = cameraInfo!!.zoomState.value?.zoomRatio ?: 0F

                // Get the pinch gesture's scaling factor
                val delta = detector.scaleFactor

                // Update the camera's zoom ratio. This is an asynchronous operation that returns
                // a ListenableFuture, allowing you to listen to when the operation completes.
                cameraController!!.setZoomRatio(currentZoomRatio * delta)

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
    }



    private fun newJpgFileName() : String {
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "${filename}.jpg"
    }
    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply {
                if(!this.exists()){
                    mkdirs()
                }
            }
        }
        return if (mediaDir != null && mediaDir.exists()) mediaDir
        else filesDir
    }



    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}

