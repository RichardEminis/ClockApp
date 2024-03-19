package com.example.clockapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ClockAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MainFragment("GMT+3")
            1 -> MainFragment("GMT+5")
            2 -> MainFragment("GMT-4")
            3 -> MainFragment("GMT")
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}