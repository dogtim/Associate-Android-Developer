package com.example.associate.training

import com.example.associate.training.java.User
import org.junit.Test

import org.junit.Assert.*

class KotlinConvesionTest {

    @Test
    fun equality_test() {
        // Structural equality uses the == operator and calls equals() to determine if two instances are equal.
        // Referential equality uses the === operator and checks if two references point to the same object.

        val user1 = User("Jane", "Doe")
        val user2 = User("Jane", "Doe")
        // structurallyEqual
        assertTrue(user1 == user2)
        // referentiallyEqual
        assertFalse(user1 === user2)
    }

    @Test
    fun argument_test() {
        // usage
        val jane = AnotherUser("Jane") // same as AnotherUser("Jane", null)
        val joe = AnotherUser("Joe", "Doe")

        assertNull(jane.lastName)
        assertNotNull(joe.lastName)

        // Kotlin allows you to label your arguments when your functions are called:
        val john = AnotherUser(firstName = "John", lastName = "Doe")
        // usage
        val onlyLastName = AnotherUser(lastName = "Doe") // same as User(null, "Doe")
        assertNull(onlyLastName.firstName)
        assertNotNull(john.lastName)
    }

}

data class AnotherUser(var firstName: String? = null, var lastName: String? = null)