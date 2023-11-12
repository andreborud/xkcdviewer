package com.andreborud.xkcdviewer.extensions

import android.text.InputFilter
import android.text.Spanned

class ComicNumberFilter(private val min: Int, private val max: Int) : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        val currentText = dest.toString()
        val newText = currentText.substring(0, dstart) + source + currentText.substring(dend)
        return if (isValidNumber(newText)) null else ""
    }

    fun isValidNumber(input: String): Boolean {
        return try {
            val number = input.toInt()
            number in min..max
        } catch (nfe: NumberFormatException) {
            false
        }
    }
}