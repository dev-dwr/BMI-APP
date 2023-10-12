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
        private var isImperialUnit = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextWeight: EditText = findViewById(R.id.editTextWeight)
        val weightUnitVIew: TextView = findViewById(R.id.kg_view)
        val heightUnitView: TextView = findViewById(R.id.cm_view)
        val inchesTextHeight: TextView = findViewById(R.id.inchesTextHeight)
        val inchesTextView: TextView = findViewById(R.id.inchesTextView)
        val editTextHeight: EditText = findViewById(R.id.editTextHeight)
        val buttonCalculate: Button = findViewById(R.id.buttonCalculate)
        val textViewResult: TextView = findViewById(R.id.textViewResult)
        val buttonDetails: Button = findViewById(R.id.detailsButton)
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        buttonCalculate.setOnClickListener {
            processBMICalculation(
                editTextWeight,
                editTextHeight,
                textViewResult,
                editTextHeight,
                buttonDetails
            )
        }
        buttonDetails.setOnClickListener { processBMIDetails(textViewResult) }


        customizeToolbar(toolbar)
        toolbar.setOnClickListener {
            showDropdownMenu(
                it,
                weightUnitVIew,
                heightUnitView,
                inchesTextView,
                inchesTextHeight
            )
        }
    }

    private fun customizeToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar?.title = "⋮"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun processBMICalculation(
        editTextWeight: EditText,
        editTextHeight: EditText,
        textViewResult: TextView,
        inchesTextHeight: EditText,
        buttonDetails: Button
    ) {
        val weight = editTextWeight.text.toString().toFloatOrNull()
        val height = editTextHeight.text.toString().toFloatOrNull()
        val inches = inchesTextHeight.text.toString().toFloatOrNull()
        when {
            isImperialUnit -> calculateNormalBMI(weight, height, textViewResult, buttonDetails)
            else -> calculateImperialBMI(weight, height, inches, textViewResult, buttonDetails)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateNormalBMI(
        weight: Float?,
        height: Float?,
        textViewResult: TextView,
        buttonDetails: Button
    ) {
        if (weight != null && height != null && height != 0f && weight > 0 && height > 0) {
            val bmi = calculateBMI(weight, heightInMeters = height / 100)
            bmiHistory.add(BMIHistory(bmi, LocalDateTime.now()))
            textViewResult.text = "Your BMI is: ${String.format("%.2f", bmi)}"
            buttonDetails.visibility = View.VISIBLE
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateImperialBMI(
        pounds: Float?,
        feet: Float?,
        inches: Float?,
        textViewResult: TextView,
        buttonDetails: Button
    ) {
        if (pounds != null && feet != null && inches != null) {
            val bmi = calculateBMI(feet, inches, pounds)
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


    private fun showDropdownMenu(
        anchor: View,
        weightUnit: TextView,
        heightUnit: TextView,
        inchesTextView: TextView,
        inchesTextHeight: TextView
    ) {
        val popupMenu = PopupMenu(this, anchor)
        popupMenu.menuInflater.inflate(R.menu.dropdown_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.change_unit -> {
                    isImperialUnit = !isImperialUnit
                    setUnits(
                        isImperialUnit,
                        weightUnit,
                        heightUnit,
                        inchesTextView,
                        inchesTextHeight
                    )
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

    private fun setUnits(
        kgAndMeters: Boolean,
        weightUnit: TextView,
        heightUnit: TextView,
        inchesTextView: TextView,
        inchesTextHeight: TextView
    ) {
        if (kgAndMeters) {
            weightUnit.text = "kg"
            heightUnit.text = "cm"
            inchesTextView.visibility = View.INVISIBLE
            inchesTextHeight.visibility = View.INVISIBLE
        } else {
            weightUnit.text = "pounds"
            heightUnit.text = "feet"
            inchesTextView.visibility = View.VISIBLE
            inchesTextHeight.visibility = View.VISIBLE
        }
    }

    private fun startBMIHistoryActivity() {
        val intent = Intent(this, BMIHistoryActivity::class.java)
        startActivity(intent)
    }
}