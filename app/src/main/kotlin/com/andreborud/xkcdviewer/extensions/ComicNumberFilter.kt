package com.andreborud.xkcdviewer.extensions

import android.text.InputFilter
import android.text.Spanned

class ComicNumberFilter(private val min: Int, private val max: Int) : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        try {
            val inputString = dest.subSequence(0, dstart).toString() + source + dest.subSequence(dend, dest.length)
            val inputNumber = inputString.toInt()
            if (isInRange(min, max, inputNumber)) {
                return null
            }
        } catch (nfe: NumberFormatException) { }
        return ""
    }

    private fun isInRange(min: Int, max: Int, number: Int): Boolean {
        return number in min..max
    }
}