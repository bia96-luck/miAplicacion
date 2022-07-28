package com.example.miaplicacion.view.ui.usecases.create

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.miaplicacion.aplication.miAplicacionApplication
import com.example.miaplicacion.databinding.ActivityCameraScannerBinding
import com.example.miaplicacion.view.tost
import com.example.miaplicacion.view.ui.MainActivity
import com.example.miaplicacion.viewmodel.create.ActivityCameraScannerViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

private const val CAMERA_PERMISSION_REQUEST_CODE = 1

class CameraScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraScannerBinding

    // instancia del viewModel
    private val activityCameraScannerViewModel: ActivityCameraScannerViewModel by viewModels() {
        ActivityCameraScannerViewModel.Factory((application
                as miAplicacionApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityCameraScannerViewModel.storeName = intent.getStringExtra("storeName").toString()
        activityCameraScannerViewModel.storeNotes = intent.getStringExtra("notes").toString()
        activityCameraScannerViewModel.storeType = intent.getStringExtra("type").toString()

        if (hasCameraPermission()) bindCameraUseCases() //En caso true llamamos a esta función
        else requestPermission()

    }
    //
    private fun processBarcode(barcodeList: List<Barcode>, scanner: BarcodeScanner) {
        if (barcodeList.isNotEmpty()) {
            with (barcodeList.first()) {

                val rawValue = this.rawValue.toString()
                val barcodeType = this.format.toString()
                activityCameraScannerViewModel.rawValue = rawValue
                activityCameraScannerViewModel.barcodeType = barcodeType
                activityCameraScannerViewModel.setNewCard()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
                scanner.close()
                finishAffinity()
                this@CameraScannerActivity.finish()

            }
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun processImageProxy(scanner: BarcodeScanner, imageProxy: ImageProxy) {
        imageProxy.image?.let { image ->
            val inputImage = InputImage.fromMediaImage(
                image,
                imageProxy.imageInfo.rotationDegrees
            )

            scanner.process(inputImage).addOnSuccessListener { barcodeList ->
                processBarcode(barcodeList, scanner)
            }

        }?.addOnFailureListener {
            // This failure will happen if the barcode scanning model
            // fails to download from Google Play Services
            Log.e("camera", it.message.orEmpty())
        }?.addOnCompleteListener {
            // When the image is from CameraX analysis use case, must
            // call image.close() on received images when finished
            // using them. Otherwise, new images may not be received
            // or the camera may stall.

            imageProxy.image?.close()
            imageProxy.close()

        }
    }

    //
    private fun bindCameraUseCases() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)//Crea una isntancia del proceso de la camara

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // setting up the preview use case
            val previewUseCase = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.cameraView.surfaceProvider)
            }

            // configure to use the back camera
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA //elegimos la cámara

            // configure our MLKit BarcodeScanning client
            /* passing in our desired barcode formats - MLKit supports additional
            formats outside of the ones listed here, and you may not need to offer support
            for all of these.
            You should only specify the ones you need */
            val options = BarcodeScannerOptions.Builder().setBarcodeFormats(
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_AZTEC,
                Barcode.FORMAT_CODE_39,
                Barcode.FORMAT_CODE_93,
                Barcode.FORMAT_EAN_8,
                Barcode.FORMAT_EAN_13,
                Barcode.FORMAT_QR_CODE,
                Barcode.FORMAT_UPC_A,
                Barcode.FORMAT_UPC_E,
                Barcode.FORMAT_PDF417,
                Barcode.FORMAT_ITF,
                Barcode.FORMAT_DATA_MATRIX,
                Barcode.FORMAT_CODABAR
            ).build()
            // getClient() creates a new instance of the MLKit barcode scanner with the specified options
            val scanner = BarcodeScanning.getClient(options)

            // setting up the analysis use case
            val analysisUseCase = ImageAnalysis.Builder().build()

            // define the actual functionality of our analysis use case
            analysisUseCase.setAnalyzer(
                // newSingleThreadExecutor() will let us perform analysis on a single worker thread
                Executors.newSingleThreadExecutor()
            ) { imageProxy ->
                processImageProxy(scanner, imageProxy)
            }

            try {
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    previewUseCase,
                    analysisUseCase
                )

            } catch (illegalStateException: IllegalStateException) {
                // If the use case has already been bound to another lifecycle or method is not called on main thread.
                Log.e("camera", illegalStateException.message.orEmpty())
            } catch (illegalArgumentException: IllegalArgumentException) {
                // If the provided camera selector is unable to resolve a camera to be used for the given use cases.
                Log.e("camera", illegalArgumentException.message.orEmpty())
            }

        }, ContextCompat.getMainExecutor(this))

    }

    // Compreba los permisos
    private fun hasCameraPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    //Pregunta si esta de acuerdo para dar permisos
    private fun requestPermission() {
        // opening up dialog to ask for camera permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }
    //Hace una cosa u otro en función de si le hemos dado o no permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // user granted permissions - we can set up our scanner
            bindCameraUseCases()
        } else {
            // user did not grant permissions - we can't use the camera
            this.tost("Se necesitan permisos de cámara")

        }
    }
}