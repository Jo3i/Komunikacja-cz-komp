//package com.example.ocrapp
//
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.net.Uri
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.result.PickVisualMediaRequest
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.appcompat.app.AppCompatActivity
//import androidx.camera.core.CameraSelector
//import androidx.camera.core.ImageCapture
//import androidx.camera.core.ImageCaptureException
//import androidx.camera.core.Preview
//import androidx.camera.lifecycle.ProcessCameraProvider
//import androidx.core.content.ContextCompat
//import com.example.ocrapp.databinding.ActivityScanBinding
//import com.google.mlkit.vision.common.InputImage
//import com.google.mlkit.vision.text.TextRecognition
//import com.google.mlkit.vision.text.latin.TextRecognizerOptions
//import org.matheclipse.parser.client.eval.DoubleEvaluator
//import java.io.File
//import java.text.SimpleDateFormat
//import java.util.*
//
//class ScanActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityScanBinding  // Binding do layoutu
//    private var imageCapture: ImageCapture? = null      // Obiekt do robienia zdjęć
//
//    // Launcher do proszenia o pozwolenie na kamerę
//    private val cameraPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
//            if (granted) startCamera()
//            else {
//                Toast.makeText(this, "Brak pozwolenia na kamerę", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//        }
//
//    // Launcher do wyboru zdjęcia z galerii (Android 14+)
//    private val pickPhotoLauncher = registerForActivityResult(
//        ActivityResultContracts.PickVisualMedia()
//    ) { uri: Uri? ->
//        uri?.let { processImageUri(it) } // Przetwarzanie wybranego zdjęcia
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityScanBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Kliknięcie przycisku "Skanuj" – wykonuje zdjęcie
//        binding.captureButton.setOnClickListener { takePhoto() }
//
//        // Kliknięcie na podgląd kamery otwiera galerię
//        binding.viewFinder.setOnClickListener {
//            pickPhotoLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
//        }
//
//        // Sprawdzenie pozwolenia na kamerę i uruchomienie kamery
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//            == PackageManager.PERMISSION_GRANTED) startCamera()
//        else cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
//    }
//
//    // Uruchamia podgląd kamery
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//        cameraProviderFuture.addListener({
//            val cameraProvider = cameraProviderFuture.get()
//
//            // Konfiguracja podglądu
//            val preview = Preview.Builder().build().also {
//                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
//            }
//
//            // Konfiguracja ImageCapture
//            imageCapture = ImageCapture.Builder().build()
//
//            try {
//                cameraProvider.unbindAll() // Odwiązanie poprzednich użyć kamery
//                cameraProvider.bindToLifecycle(
//                    this, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture
//                )
//            } catch (e: Exception) {
//                Toast.makeText(this, "Nie udało się uruchomić kamery", Toast.LENGTH_SHORT).show()
//            }
//        }, ContextCompat.getMainExecutor(this))
//    }
//
//    // Robi zdjęcie i zapisuje do pliku tymczasowego
//    private fun takePhoto() {
//        val photoFile = File(
//            externalCacheDir,
//            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis()) + ".jpg"
//        )
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//        imageCapture?.takePicture(
//            outputOptions, ContextCompat.getMainExecutor(this),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                    processImageFile(photoFile) // OCR na zapisanym zdjęciu
//                }
//
//                override fun onError(exception: ImageCaptureException) {
//                    Toast.makeText(this@ScanActivity, "Błąd podczas robienia zdjęcia", Toast.LENGTH_SHORT).show()
//                }
//            }
//        )
//    }
//
//    // Przetwarza zdjęcie z pliku na InputImage (ML Kit)
//    private fun processImageFile(file: File) {
//        val image = InputImage.fromFilePath(this, Uri.fromFile(file))
//        processImage(image)
//    }
//
//    // Przetwarza zdjęcie wybrane z galerii
//    private fun processImageUri(uri: Uri) {
//        val image = InputImage.fromFilePath(this, uri)
//        processImage(image)
//    }
//
//    // Główna metoda OCR i wyliczania wyniku
//    private fun processImage(image: InputImage) {
//        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS) // Klient ML Kit
//        recognizer.process(image)
//            .addOnSuccessListener { visionText ->
//                val recognizedText = visionText.text.trim() // Rozpoznany tekst
//
//                // Najpierw próbujemy obliczyć matematyczne wyrażenie
//                val mathResult = evaluateMathExpression(recognizedText)
//                if (mathResult != "Błąd") {
//                    // Jeśli udało się obliczyć matematycznie, wysyłamy wynik do historii
//                    Toast.makeText(this, "Wynik matematyczny: $mathResult", Toast.LENGTH_LONG).show()
//                    sendResultToHistory("Math: $recognizedText = $mathResult")
//                } else {
//                    // Jeśli nie jest to matematyka, wysyłamy do AI online (przygotowane miejsce)
//                    sendToAI(recognizedText)
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(this, "Nie udało się rozpoznać tekstu", Toast.LENGTH_SHORT).show()
//            }
//    }
//
//    // Próba obliczenia wyrażenia matematycznego
//    private fun evaluateMathExpression(expression: String): String {
//        return try {
//            val evaluator = DoubleEvaluator()
//            val value = evaluator.evaluate(expression)
//            value.toString()
//        } catch (_: Exception) {
//            "Błąd"
//        }
//    }
//
//    // Wysyła wynik do HistoryActivity zamiast SharedPreferences
//    private fun sendResultToHistory(result: String) {
//        val intent = Intent(this, HistoryActivity::class.java)
//        intent.putExtra("result", result) // Wysyłamy wynik
//        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        startActivity(intent)
//    }
//
//    // Tutaj możesz podłączyć AI dla zadań otwartych
//    private fun sendToAI(text: String) {
//        // TODO: podłącz np. Puter.js lub inne API
//        // Przykład:
//        // val aiResult = callAI(text)
//        // sendResultToHistory("AI: $aiResult")
//
//        Toast.makeText(this, "Zadanie otwarte (wysyłane do AI): $text", Toast.LENGTH_LONG).show()
//        sendResultToHistory("OpenTask: $text")
//    }
//
//    // Powrót do MainMenu
//    fun onBackToMenuClick(_view: android.view.View) {
//        finish()
//    }
//}
