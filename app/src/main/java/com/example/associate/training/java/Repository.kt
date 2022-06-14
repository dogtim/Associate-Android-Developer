package com.example.associate.training.java

import java.util.ArrayList
object Repository {

    val users: List<User>
        get() = _users
    private val _users = mutableListOf<User>()

    val formattedUserNames: List<String>
        get() {
            return users.map { user -> user.formattedName }
        }

    // keeping the constructor private to enforce the usage of getInstance
    init {
        val user1 = User("Jane", "")
        val user2 = User("John", null)
        val user3 = User("Anne", "Doe")
        _users.apply {
            // this == _users
            add(user1)
            add(user2)
            add(user3)
        }
    }
}