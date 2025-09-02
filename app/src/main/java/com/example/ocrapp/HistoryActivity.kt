package com.example.ocrapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ocrapp.databinding.ActivityHistoryBinding
import com.example.ocrapp.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val historyList = mutableListOf<HistoryItem>() // Lista HistoryItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Dodaj przykładowe dane
        historyList.add(
            HistoryItem(
                studentName = "Jan Kowalski",
                date = "2025-09-02 10:00:00",
                subject = "Matematyka",
                result = "5"
            )
        )
        historyList.add(
            HistoryItem(
                studentName = "Anna Nowak",
                date = "2025-09-01 15:30:00",
                subject = "Język polski",
                result = "Poprawnie"
            )
        )

        // Odbierz nowy wynik z ScanActivity
        intent.getSerializableExtra("historyItem")?.let { item ->
            if (item is HistoryItem) {
                historyList.add(0, item) // dodajemy nowy wynik na początek
            }
        }

        // RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = HistoryAdapter(historyList)
    }

    // Adapter RecyclerView dla HistoryItem
    class HistoryAdapter(private val items: List<HistoryItem>) :
        RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

        inner class HistoryViewHolder(val binding: ItemHistoryBinding) :
            RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): HistoryViewHolder {
            val binding = ItemHistoryBinding.inflate(
                android.view.LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HistoryViewHolder(binding)
        }

        override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
            val item = items[position]
            holder.binding.apply {
                textStudentName.text = item.studentName
                textDate.text = item.date
                textSubject.text = item.subject
                textResult.text = item.result
            }
        }

        override fun getItemCount(): Int = items.size
    }
}
