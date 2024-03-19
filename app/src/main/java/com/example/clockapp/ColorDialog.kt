package com.example.clockapp

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class ColorDialog : DialogFragment() {

    private var initialColor: Int = Color.BLACK
    private lateinit var colorSelectedListener: (Int) -> Unit

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Choose Color")
                .setPositiveButton("OK") { _: DialogInterface, _: Int ->
                    colorSelectedListener(initialColor)
                }
                .setNegativeButton("Cancel") { dialog: DialogInterface, _: Int ->
                    dialog.cancel()
                }
                .setSingleChoiceItems(
                    arrayOf("Red", "Green", "Blue", "Yellow", "Black"),
                    getColorIndex(initialColor)
                ) { dialog, which ->
                    initialColor = when (which) {
                        0 -> Color.RED
                        1 -> Color.GREEN
                        2 -> Color.BLUE
                        3 -> Color.YELLOW
                        else -> Color.BLACK
                    }
                    colorSelectedListener(initialColor)
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun setOnColorSelectedListener(listener: (Int) -> Unit) {
        colorSelectedListener = listener
    }

    private fun getColorIndex(color: Int): Int {
        return when (color) {
            Color.RED -> 0
            Color.GREEN -> 1
            Color.BLUE -> 2
            Color.YELLOW -> 3
            else -> 4
        }
    }

    companion object {
        fun newInstance(initialColor: Int): ColorDialog {
            val dialog = ColorDialog()
            dialog.initialColor = initialColor
            return dialog
        }
    }
}