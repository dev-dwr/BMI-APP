package com.pwr266521.bmiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.pwr266521.bmiapp.util.BMIUtil.BMI_KEY
import com.pwr266521.bmiapp.util.BMIUtil.bmiDescriptions
import com.pwr266521.bmiapp.util.BMIUtil.getBMICategory

class BMIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        val goBackBtn: Button = findViewById(R.id.go_back_button)
        val bmiResult: TextView = findViewById(R.id.your_bmi_result)
        val bmiDescription: TextView = findViewById(R.id.your_bmi_description)

        intent.extras?.getString(BMI_KEY)?.let {
            val bmiCategory = getBMICategory(it.toFloat())
            bmiResult.text = "BMI: ${it}, ${bmiCategory}"
            bmiDescription.text = bmiDescriptions[bmiCategory]

        }

        goBackBtn.setOnClickListener { finish() }
    }
}