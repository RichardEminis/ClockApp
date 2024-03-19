package com.example.clockapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.clockapp.databinding.FragmentMainBinding
import java.util.Calendar
import java.util.TimeZone


class MainFragment(private val timeZone: String) : Fragment()  {

    private val binding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    private lateinit var clockView1: ClockView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)

        clockView1 = rootView.findViewById(R.id.clockView1)
        updateTime()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorChanger()
    }

    private fun colorChanger() {
        sharedPreferences =
            requireContext().getSharedPreferences(CLOCK_SETTINGS, Context.MODE_PRIVATE)

        val circleMainColor = sharedPreferences.getInt(CIRCLE_MAIN_COLOR, Color.LTGRAY)
        val secondHandColor = sharedPreferences.getInt(SECOND_HAND_COLOR, Color.RED)
        val circleFrameColor = sharedPreferences.getInt(CIRCLE_FRAME_COLOR, Color.DKGRAY)

        binding.btnChangeCircleMainColor.setBackgroundColor(circleMainColor)
        binding.btnChangeSecondHandColor.setBackgroundColor(secondHandColor)
        binding.btnChangeCircleFrameColor.setBackgroundColor(circleFrameColor)

        clockView1 = binding.clockView1
        clockView1.setCircleMainColor(circleMainColor)
        clockView1.setSecondHandColor(secondHandColor)
        clockView1.setCircleFrameColor(circleFrameColor)

        binding.btnChangeCircleMainColor.setOnClickListener {
            val colorPickerDialog = ColorDialog.newInstance(circleMainColor)
            colorPickerDialog.setOnColorSelectedListener { color ->
                binding.btnChangeCircleMainColor.setBackgroundColor(color)
                clockView1.setCircleMainColor(color)
                sharedPreferences.edit().putInt(CIRCLE_MAIN_COLOR, color).apply()
            }
            colorPickerDialog.show(parentFragmentManager, COLOR_PICKER_DIALOG)
        }

        binding.btnChangeSecondHandColor.setOnClickListener {
            val colorPickerDialog = ColorDialog.newInstance(secondHandColor)
            colorPickerDialog.setOnColorSelectedListener { color ->
                binding.btnChangeSecondHandColor.setBackgroundColor(color)
                clockView1.setSecondHandColor(color)
                sharedPreferences.edit().putInt(SECOND_HAND_COLOR, color).apply()
            }
            colorPickerDialog.show(parentFragmentManager, COLOR_PICKER_DIALOG)
        }

        binding.btnChangeCircleFrameColor.setOnClickListener {
            val colorPickerDialog = ColorDialog.newInstance(circleFrameColor)
            colorPickerDialog.setOnColorSelectedListener { color ->
                binding.btnChangeCircleFrameColor.setBackgroundColor(color)
                clockView1.setCircleFrameColor(color)
                sharedPreferences.edit().putInt(CIRCLE_FRAME_COLOR, color).apply()
            }
            colorPickerDialog.show(parentFragmentManager, COLOR_PICKER_DIALOG)
        }
    }

    private fun updateTime() {
        val timeZone = TimeZone.getTimeZone(timeZone)
        timeZone?.let {
            val calendar = Calendar.getInstance(it)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)
            val second = calendar.get(Calendar.SECOND)

            clockView1.setTime(hour, minute, second)
            clockView1.postInvalidateDelayed(SECOND_DELAY)
        }
    }
}