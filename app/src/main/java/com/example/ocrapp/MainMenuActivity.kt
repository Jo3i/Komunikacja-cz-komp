package com.example.ocrapp

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }
    fun onButtonClick(view: View) {
        // Tu możesz dodać logikę otwierania nowej aktywności
        Toast.makeText(this, "Kliknięto przycisk", Toast.LENGTH_SHORT).show()
    }
    fun openSettings(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

}
