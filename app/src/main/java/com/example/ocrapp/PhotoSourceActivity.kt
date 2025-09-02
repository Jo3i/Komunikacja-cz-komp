package com.example.ocrapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PhotoSourceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_source)

        val buttonCamera = findViewById<Button>(R.id.button_camera)
        val buttonGallery = findViewById<Button>(R.id.button_gallery)

        // Przejście do ScanActivity w trybie Kamera
        buttonCamera.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("mode", "camera")
            startActivity(intent)
        }

        // Przejście do ScanActivity w trybie Galeria
        buttonGallery.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            intent.putExtra("mode", "gallery")
            startActivity(intent)
        }
    }
}
