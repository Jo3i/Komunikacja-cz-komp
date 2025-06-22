package com.example.ocrapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val historyList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentNameText: TextView = itemView.findViewById(R.id.textStudentName)
        val dateText: TextView = itemView.findViewById(R.id.textDate)
        val resultText: TextView = itemView.findViewById(R.id.textResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]
        holder.studentNameText.text = item.studentName
        holder.dateText.text = item.date
        holder.resultText.text = item.result
    }

    override fun getItemCount() = historyList.size
}
