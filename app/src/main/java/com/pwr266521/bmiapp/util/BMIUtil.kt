package com.pwr266521.bmiapp.util

import com.pwr266521.bmiapp.domain.BMIHistory

object BMIUtil {
    val bmiHistory = mutableListOf<BMIHistory>()

    val bmiDescriptions = mapOf(
        "Underweight" to "This category signifies that your weight is considered lower than the normal range. Consult with healthcare professionals for advice.",
        "Normal weight" to "This category signifies that your weight is within the normal range. Continue with a balanced diet and regular exercise.",
        "Overweight" to "This category indicates that your weight is above the normal range. Consider a balanced diet and regular physical activity.",
        "Obesity I" to "This is the first stage of obesity. It's recommended to consult with healthcare professionals for guidance.",
        "Obesity II" to "This is the second stage of obesity. It's strongly recommended to seek medical advice.",
        "Obesity III" to "This is the most severe stage of obesity. Immediate medical intervention is suggested.",
        "Invalid BMI" to "The provided BMI doesn't fit into any standard category."
    )

    const val BMI_KEY = "bmiKey"
    fun calculateBMI(weight: Float, heightInMeters: Float): Float {
        return weight / (heightInMeters * heightInMeters)
    }

    fun getBMICategory(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi in 18.5..24.9 -> "Normal weight"
            bmi in 25.0..29.9 -> "Overweight"
            bmi in 30.0..34.9 -> "Obesity I"
            bmi in 35.0..39.9 -> "Obesity II"
            bmi >= 40 -> "Obesity III"
            else -> "Invalid BMI"
        }
    }
}