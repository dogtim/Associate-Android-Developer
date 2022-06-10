package com.example.associate.training

import org.junit.Test

import org.junit.Assert.*

/**
 * Inheritance v.s Delegation in Kotlin
 */
class KotlinInheritanceDelegationTest {

    @Test
    fun inheritence_test() {
        assertEquals("wow wow!",Dog().sayHello());
        assertEquals("wif wif!",Yorkshire().sayHello());
    }

    @Test
    fun delegate_test() {
        val upperCase = UpperCase("haha");
        val elvisPresley = ElvisPresley("Smile");

        assertEquals("HAHA",upperCase.makeSound());
        assertEquals("SuperIdol",elvisPresley.makeSound());
    }

}
// Inheritance
open class Dog {
    open fun sayHello(): String {
        return "wow wow!"
    }
}

class Yorkshire : Dog() {       // 3
    override fun sayHello(): String {   // 4
        return "wif wif!"
    }
}

// Delegation
// Only interfaces can be delegated to
interface SoundBehavior {
    fun makeSound(): String
}

class ScreamBehavior(val n: String): SoundBehavior {
    override fun makeSound(): String {
        return "${n.toUpperCase()}"
    }
}

class RockAndRollBehavior(val n: String): SoundBehavior {
    override fun makeSound(): String {
        return "SuperIdol"
    }
}

// Tom Araya is the "singer" of Slayer
class UpperCase(n:String): SoundBehavior by ScreamBehavior(n)

// You should know ;)
class ElvisPresley(n:String): SoundBehavior by RockAndRollBehavior(n)