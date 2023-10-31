package com.pwr266521.bmiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AboutMeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_me)
        val goBackBtn: Button = findViewById(R.id.back_btn)
        goBackBtn.setOnClickListener { finish() }

    }
}