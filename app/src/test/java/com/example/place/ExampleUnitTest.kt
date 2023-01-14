package com.example.place

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_Lat_Long() {
        val Check = CorrectLatLong()
        assertEquals(true, Check.test("-33.8523341", "151.2106085"))
    }
}