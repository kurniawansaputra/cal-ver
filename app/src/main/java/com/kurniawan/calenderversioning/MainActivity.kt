package com.kurniawan.calenderversioning

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.kurniawan.calenderversioning.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up date picker listener
        binding.datePicker.init(
            binding.datePicker.year,
            binding.datePicker.month,
            binding.datePicker.dayOfMonth
        ) { _, year, monthOfYear, dayOfMonth ->
            result(dayOfMonth, monthOfYear + 1, year)
        }

        // Initialize result with the current date
        result(binding.datePicker.dayOfMonth, binding.datePicker.month + 1, binding.datePicker.year)
    }

    private fun result(day: Int, month: Int, year: Int) {
        binding.apply {
            val calendarVersion = getCalendarVersion(day, month, year)
            textResult.text = calendarVersion
        }
    }

    private fun getCalendarVersion(day: Int, month: Int, year: Int): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
        }
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        val displayMonth = calendar.get(Calendar.MONTH) + 1
        val twoDigitYear = year % 100
        return "Version $twoDigitYear.$displayMonth.$weekOfYear"
    }
}