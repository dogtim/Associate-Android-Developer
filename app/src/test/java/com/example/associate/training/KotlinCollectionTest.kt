package com.example.associate.training

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class KotlinCollectionTest {
    @Test
    fun collection_method_test() {
        val numbers = listOf(0, 3, 8, 4, 0)
        // println("list: $numbers")
        val setOfNumbers = numbers.toSet()
        assertArrayEquals(listOf(0, 0, 3, 4, 8).toIntArray(), numbers.sorted().toIntArray())
        assertArrayEquals(listOf(0, 3, 8, 4).toIntArray(), setOfNumbers.toIntArray())

        assertTrue(setOfNumbers.contains(0))
        assertFalse(setOfNumbers.contains(1))
    }

    @Test
    fun map_method_test() {
        // Higher-order functions
        val peopleAges = mutableMapOf<String, Int>(
            "Fred" to 30,
            "Ann" to 23
        )
        assertEquals(30, peopleAges["Fred"])
        assertNull(peopleAges["Fred23"])
        peopleAges["Fred"] = 50
        assertEquals(50, peopleAges["Fred"])

        peopleAges.put("Barbara", 42)
        peopleAges["Joe"] = 51

        // foreach
        peopleAges.forEach {
            if (it.key == "Joe") {
                assertEquals(51, peopleAges[it.key])
            }
        }

        // map
        val mapString = peopleAges.map { "${it.key} is ${it.value}" }.joinToString(", ")
        assertEquals("Fred is 50, Ann is 23, Barbara is 42, Joe is 51", mapString)

        // filter
        val filteredNames = peopleAges.filter { it.key.length < 4 }
        assertNull(filteredNames["Barbara"])
        assertNotNull(filteredNames["Joe"])
    }

    @Test
    fun lambda_test() {
        // below is a lambda function, triple is a block or function you could pass it and execute it in runtime
        val triple: (Int) -> Int = { a: Int -> a * 3 }
        assertEquals(15, triple(5))

        // High-Level sorted and usage
        val peopleNames = listOf("Fred", "Ann", "Barbara", "Joe")

        // Define your comparable function
        val sortedNames =
            peopleNames.sortedWith { str1: String, str2: String -> str1.length - str2.length }
        assertEquals(listOf("Ann", "Joe", "Fred", "Barbara").toString(), sortedNames.toString())
    }

    fun string_test() {
        val words = listOf(
            "about",
            "acute",
            "awesome",
            "balloon",
            "best",
            "brief",
            "class",
            "coffee",
            "creative"
        )

        val filteredWords = words.filter { it.startsWith("b", ignoreCase = true) }
        assertTrue(filteredWords.contains("balloon"))
        assertFalse(filteredWords.contains("coffee"))
    }
}