package com.pwr266521.bmiapp

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.pwr266521.bmiapp.domain.BMIHistory
import com.pwr266521.bmiapp.util.BMIUtil.BMI_KEY
import com.pwr266521.bmiapp.util.BMIUtil.bmiHistory
import com.pwr266521.bmiapp.util.BMIUtil.calculateBMI
import java.time.LocalDateTime

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.pwr266521.bmiapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    companion object {
        private const val BMIDelimiter = ":"
        private var isImperialUnit = false
    }

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editTextHeight = binding.editTextHeight
        val buttonCalculate = binding.buttonCalculate
        val textViewResult = binding.textViewResult
        val buttonDetails = binding.detailsButton
        val toolbar = binding.toolbar
        val editTextWeight = binding.editTextWeight


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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dropdown_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.change_unit -> {
                isImperialUnit = !isImperialUnit
                setUnits(
                    isImperialUnit,
                    binding.kgView,
                    binding.cmView,
                    binding.inchesTextView,
                    binding.inchesTextHeight
                )
                true
            }

            R.id.bmi_history -> {
                startBMIHistoryActivity()
                true
            }

            else -> false
        }
        return super.onOptionsItemSelected(item)
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

    private fun customizeToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
    }

    private fun startBMIHistoryActivity() {
        val intent = Intent(this, BMIHistoryActivity::class.java)
        startActivity(intent)
    }
}