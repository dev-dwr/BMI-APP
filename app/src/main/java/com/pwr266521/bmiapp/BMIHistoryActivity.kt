package com.pwr266521.bmiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pwr266521.bmiapp.adapter.BMIHistoryAdapter
import com.pwr266521.bmiapp.preferences.SharedPref
import com.pwr266521.bmiapp.util.BMIUtil.bmiHistory

class BMIHistoryActivity : AppCompatActivity() {
    private lateinit var sharedPref: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmihistory)
        sharedPref = SharedPref(this)

        val recyclerView: RecyclerView = findViewById(R.id.bmiHistoryRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = BMIHistoryAdapter(sharedPref.getHistory())
        recyclerView.adapter = adapter

        val goBackBtn: Button = findViewById(R.id.go_back_button)
        goBackBtn.setOnClickListener { finish() }
    }

}