package com.kuri2021.Test_Project.camera

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.ScaleGestureDetector
import android.view.View
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
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class Camera_Zoom : AppCompatActivity() {

    private var imageCapture: ImageCapture? = null

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    var imgName = "osz.png"
    var bitmap2=null



//    abstract val lifecycleOwner:LifecycleOwner

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.carnera_zoom)

        // 이미지 캡쳐하는 use case를 빌더패턴을 통해 구현한다.
        imageCapture = ImageCapture.Builder().build()

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        button1.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor()


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
                val cameraControl =
                    cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

                val cameraInfo = cameraControl.cameraInfo

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
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
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
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        // 사진 저장 장소
        val photoFile = File(outputDirectory, SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg")

        // outputFile의 Configuration을 담당
        val outputOptions = ImageCapture
            .OutputFileOptions
            .Builder(photoFile)
            .build()

        imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
                // 사진을 찍고 어떻게 저장할 지에 대한 구현부
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    val message = "Photo capture succeeded: $savedUri"
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, message)

//                    val bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageFile)
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, savedUri)
                    var test=cropImage(bitmap,iv,iv2)
                    Log.d("body_test",test.toString())
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                }
            }
        )
    }
    private fun cropImage(bitmap: Bitmap, frame: View, reference: View): ByteArray {
        val heightOriginal = frame.height
        val widthOriginal = frame.width
        val heightFrame = reference.height
        val widthFrame = reference.width
        val leftFrame = reference.left
        val topFrame = reference.top
        val heightReal = bitmap.height
        val widthReal = bitmap.width
        val widthFinal = widthFrame * widthReal / widthOriginal
        val heightFinal = heightFrame * heightReal / heightOriginal
        val leftFinal = leftFrame * widthReal / widthOriginal
        val topFinal = topFrame * heightReal / heightOriginal
        val bitmapFinal = Bitmap.createBitmap(bitmap, leftFinal, topFinal, widthFinal, heightFinal)
        val stream = ByteArrayOutputStream()
        bitmapFinal.compress(Bitmap.CompressFormat.JPEG, 100, stream) //100 is the best quality possibe
        return stream.toByteArray()
    }

    // startCamera()




//    private fun takePhoto() {
//        // Get a stable reference of the modifiable image capture use case
//        // 수정 가능한 이미지 캡처 사용 사례의 안정적인 참조 가져오기
//        val imageCapture = imageCapture ?: return
//        Log.d("body_imageCapture->", "" + imageCapture.toString())
//
//        // Create time-stamped output file to hold the image
//        // 이미지를 저장할 타임스탬프 출력 파일 생성
//        val photoFile = File(
//            outputDirectory,
//            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
//        )
//        Log.d("body_photoFile->", "" + photoFile.toString())
//
//        // Create output options object which contains file + metadata
//        // 파일 + 메타데이터를 포함하는 출력 옵션 객체 생성
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//        Log.d("body_outputOptions->", "" + outputOptions.toString())
//
//        // Set up image capture listener, which is triggered after photo has
//        // been taken
//        // 사진이 캡처된 후 트리거되는 이미지 캡처 리스너를 설정합니다.
//        // 찍은
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onError(exc: ImageCaptureException) {
//                    Log.e("body_exc->", "Photo capture failed: ${exc.message}", exc)
//                }
//
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val savedUri = Uri.fromFile(photoFile)
//                    Log.d("body_savedUri->", savedUri.toString())
//                    tv_1.text = savedUri.toString()
//                    val msg = "Photo capture succeeded: $savedUri"
//                    iv.scaleType = ImageView.ScaleType.CENTER
//                    iv.setImageURI(savedUri)
//
//                    val drawable = iv.getDrawable()
//                    tv_2.text = drawable.toString()
//
//
//                    Log.d("body_drawable->", drawable.toString())
//                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//                    Log.d("body_msg->", msg.toString())
//                    val bitmap = (iv.getDrawable() as BitmapDrawable).bitmap
//
////                    var intent=Intent(this@Camera_Zoom,camera_next::class.java)
////                    intent.put
////                    startActivity(intent)
//                    iv2.setImageBitmap(bitmap)
//
//                    Log.d("body_bitmap",bitmap.toString())
//                    savebitmaptojpg(bitmap,"test1","test2")
//
//                }
//            })
//    }



    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
//        ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this, "응~안되 돌아가", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }


    fun savebitmaptojpg(bitmap: Bitmap, strfilepath: String, filename: String) {
        var file = File(strfilepath)
        if (!file.exists()) {
            file.mkdir()
        }

        var fileItem=File(strfilepath+filename)
        var outputStream: OutputStream? =null

        try{
            fileItem.createNewFile()
            outputStream=FileOutputStream(fileItem)

            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
        }catch (e:java.lang.Exception){
            e.printStackTrace()
        }finally {
            try{
                if (outputStream != null) {
                    outputStream.close()
                }
            }catch (e:IOException){
                e.printStackTrace()
            }
        }

    }
}
