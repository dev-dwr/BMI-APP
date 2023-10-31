package com.pwr266521.bmiapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pwr266521.bmiapp.R
import com.pwr266521.bmiapp.domain.BMIHistory
import com.pwr266521.bmiapp.util.BMIUtil
import java.lang.Math.round
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

class BMIHistoryAdapter (private val history: List<BMIHistory>) : RecyclerView.Adapter<BMIHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = history[position]
        val bmiColor = when(BMIUtil.getBMICategory(history.bmiValue)) {
            "Underweight" -> Color.YELLOW
            "Normal weight" -> Color.GREEN
            else -> Color.RED
        }
        holder.bmiValueTextView.text = String.format("%.2f", history.bmiValue)
        holder.bmiValueTextView.setTextColor(bmiColor)
        holder.createdAtTextView.text = history.createAt
    }

    override fun getItemCount(): Int = history.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bmiValueTextView: TextView = view.findViewById(R.id.bmiValueTextView)
        val createdAtTextView: TextView = view.findViewById(R.id.createdAtTextView)
    }

}