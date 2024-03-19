package com.example.clockapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ClockAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        val fragment = MainFragment()
        val args = Bundle()
        when (position) {
            0 -> args.putString(TIME_ZONE, GMT_3)
            1 -> args.putString(TIME_ZONE, UTC_5)
            2 -> args.putString(TIME_ZONE, GMT_4)
            3 -> args.putString(TIME_ZONE, GMT)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
        fragment.arguments = args
        return fragment
    }
}