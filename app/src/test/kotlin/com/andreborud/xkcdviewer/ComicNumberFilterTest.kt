package com.andreborud.xkcdviewer

import com.andreborud.xkcdviewer.extensions.ComicNumberFilter
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class ComicNumberFilterTest {

    private lateinit var filter: ComicNumberFilter

    @Before
    fun setUp() {
        filter = ComicNumberFilter(1, 2853)
    }

    @Test
    fun isValidNumber_returnsTrueForNumberWithinRange() {
        assertTrue(filter.isValidNumber("1234"))
    }

    @Test
    fun isValidNumber_returnsFalseForNumberBelowRange() {
        assertFalse(filter.isValidNumber("0"))
    }

    @Test
    fun isValidNumber_returnsFalseForNumberAboveRange() {
        assertFalse(filter.isValidNumber("3000"))
    }
}