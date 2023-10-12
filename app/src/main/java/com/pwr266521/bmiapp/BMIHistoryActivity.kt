package com.pwr266521.bmiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pwr266521.bmiapp.adapter.BMIHistoryAdapter
import com.pwr266521.bmiapp.util.BMIUtil.bmiHistory

class BMIHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmihistory)

        val recyclerView: RecyclerView = findViewById(R.id.bmiHistoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = BMIHistoryAdapter(bmiHistory)
        recyclerView.adapter = adapter

        val goBackBtn: Button = findViewById(R.id.go_back_button)
        goBackBtn.setOnClickListener { finish() }
    }

}