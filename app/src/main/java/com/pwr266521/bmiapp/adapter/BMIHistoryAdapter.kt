package com.pwr266521.bmiapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pwr266521.bmiapp.R
import com.pwr266521.bmiapp.domain.BMIHistory
import java.lang.Math.round
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

class BMIHistoryAdapter (private val history: List<BMIHistory>) : RecyclerView.Adapter<BMIHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(view)
    }

    private fun changeDateFormat(givenDate: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val date = inputFormat.parse(givenDate)
        return outputFormat.format(date)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = history[position]
        val formattedDate = changeDateFormat(history.createAt.toString())
        holder.bmiValueTextView.text = String.format("%.2f", history.bmiValue)
        holder.createdAtTextView.text = formattedDate
    }

    override fun getItemCount(): Int = history.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bmiValueTextView: TextView = view.findViewById(R.id.bmiValueTextView)
        val createdAtTextView: TextView = view.findViewById(R.id.createdAtTextView)
    }

}