package com.pwr266521.bmiapp.domain

import java.time.LocalDateTime

data class BMIHistory(private val bmiValue: Float, private val createAt: LocalDateTime)
