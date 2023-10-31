package com.pwr266521.bmiapp.preferences

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pwr266521.bmiapp.domain.BMIHistory

class SharedPref(private val context: Context) {
    companion object {
        private const val PREFS_NAME = "BMIHistoryPreferences"
        private const val HISTORY_KEY = "bmi"
    }

    private val gson = Gson()
    private val historyList: MutableList<BMIHistory> by lazy { viewHistory() }

    fun getHistory(): List<BMIHistory> = historyList

    fun addHistory(entry: BMIHistory) {
        historyList.add(entry)
        saveHistory()
    }

    private fun saveHistory() {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val historyJson = gson.toJson(historyList)
        sharedPreferences.edit().putString(HISTORY_KEY, historyJson).apply()
    }

    private fun viewHistory(): MutableList<BMIHistory> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val historyJson = sharedPreferences.getString(HISTORY_KEY, null) ?: return mutableListOf()
        val listType = object : TypeToken<MutableList<BMIHistory>>() {}.type
        return gson.fromJson(historyJson, listType)
    }
}