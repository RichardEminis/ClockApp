package com.example.clockapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.clockapp.databinding.FragmentMainBinding
import java.util.TimeZone

class MainFragment() : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var timeZone: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timeZone = requireArguments().getString(TIME_ZONE, TimeZone.getDefault().id)
        binding.clockView1.setTimeZone(timeZone)
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

        val clockView1 = binding.clockView1
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
}