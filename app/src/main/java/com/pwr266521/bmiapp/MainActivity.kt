package com.pwr266521.bmiapp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.pwr266521.bmiapp.domain.BMIHistory
import com.pwr266521.bmiapp.util.BMIUtil.BMI_KEY
import com.pwr266521.bmiapp.util.BMIUtil.bmiHistory
import com.pwr266521.bmiapp.util.BMIUtil.calculateBMI
import java.time.LocalDateTime

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    companion object {
        private const val BMIDelimiter = ":"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextWeight: EditText = findViewById(R.id.editTextWeight)
        val editTextHeight: EditText = findViewById(R.id.editTextHeight)
        val buttonCalculate: Button = findViewById(R.id.buttonCalculate)
        val textViewResult: TextView = findViewById(R.id.textViewResult)
        val buttonDetails: Button = findViewById(R.id.detailsButton)

        buttonCalculate.setOnClickListener {
            processBMICalculation(
                editTextWeight,
                editTextHeight,
                textViewResult,
                buttonDetails
            )
        }
        buttonDetails.setOnClickListener { processBMIDetails(textViewResult) }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar?.title = "⋮"

        toolbar.setOnClickListener { view ->
            showDropdownMenu(view)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processBMICalculation(
        editTextWeight: EditText,
        editTextHeight: EditText,
        textViewResult: TextView,
        buttonDetails: Button
    ) {
        val weight = editTextWeight.text.toString().toFloatOrNull()
        val height = editTextHeight.text.toString().toFloatOrNull()

        if (weight != null && height != null && height != 0f && weight > 0 && height > 0) {
            val bmi = calculateBMI(weight, heightInMeters = height / 100)
            bmiHistory.add(BMIHistory(bmi, LocalDateTime.now()))
            textViewResult.text = "Your BMI is: ${String.format("%.2f", bmi)}"
            buttonDetails.visibility = View.VISIBLE
        }
    }

    private fun processBMIDetails(textViewResult: TextView) {
        val intent = Intent(this, BMIActivity::class.java)
        val bmi = textViewResult.text
            .toString()
            .split(BMIDelimiter)[1]
            .replace(" ", "")
        intent.putExtra(BMI_KEY, bmi)
        startActivity(intent)
    }


    private fun showDropdownMenu(anchor: View) {
        val popupMenu = PopupMenu(this, anchor)
        popupMenu.menuInflater.inflate(R.menu.dropdown_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_option1 -> {
                    // Handle option 1 click
                    true
                }

                R.id.bmi_history -> {
                    startBMIHistoryActivity()
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun startBMIHistoryActivity() {
        val intent = Intent(this, BMIHistoryActivity::class.java)
        startActivity(intent)
    }
}