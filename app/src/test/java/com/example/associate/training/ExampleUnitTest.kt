package com.example.associate.training

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
    fun object_equal() {
        val model = Model("123")
        assertFalse(model.equals(null))
    }

    @Test
    fun data_class_equal() {
        val emp1 = EmployeeClass("Tim")
        val emp2 = EmployeeClass("Tim")
        assertFalse(emp1 == emp2)
        // "equals" = "=="
        assertFalse(emp1.equals(emp2))
        assertFalse(emp1 === emp2)

        val emp3 = EmployeeData("Tim")
        val emp4 = EmployeeData("Tim")
        assertTrue(emp3 == emp4)
        assertFalse(emp3 === emp4)
    }
}

class EmployeeClass (val name: String)
data class EmployeeData (val name: String)