package com.pwr266521.bmiapp.domain

import java.time.LocalDateTime

data class BMIHistory(
    val bmiValue: Float,
    val createAt: String,
    val weight: String,
    val height: String,
    val weightUnit: String,
    val heightUnit: String,
)
