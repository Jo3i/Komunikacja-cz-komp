package com.example.ocrapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ocrapp.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Przykładowe dane
        val historyItems = listOf(
            HistoryItem("Jan Kowalski", "2025-06-08", "Ocena: 4"),
            HistoryItem("Anna Nowak", "2025-06-07", "Ocena: 5"),
            HistoryItem("Krzysztof Wiśniewski", "2025-06-06", "Ocena: 3")
        )

        adapter = HistoryAdapter(historyItems)
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = adapter
    }
}
