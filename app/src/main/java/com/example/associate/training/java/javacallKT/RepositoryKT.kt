package com.example.associate.training.java.javacallKT

import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

object RepositoryKT {
    const val BACKUP_PATH = "/backup/user.repo"

    private val _users = mutableListOf<UserKT>()
    private var _nextGuestId = 1000

    @JvmStatic
    val users: List<UserKT>
        get() = _users

    @JvmStatic
    val nextGuestId
        get() = _nextGuestId++

    init {
        _users.add(UserKT(100, "josh", "Joshua Calvert", listOf("admin", "staff", "sys")))
        _users.add(UserKT(101, "dahybi", "Dahybi Yadev", listOf("staff", "nodes")))
        _users.add(UserKT(102, "sarha", "Sarha Mitcham", listOf("admin", "staff", "sys")))
        _users.add(UserKT(103, "warlow", groups = listOf("staff", "inactive")))
    }

    @JvmStatic
    @Throws(IOException::class)
    fun saveAs(path: String?):Boolean {
        val backupPath = path ?: return false

        val outputFile = File(backupPath)
        if (!outputFile.canWrite()) {
            throw FileNotFoundException("Could not write to file: $backupPath")
        }
        // Write data...
        return true
    }

    @JvmStatic
    fun addUser(user: UserKT) {
        // Ensure the user isn't already in the collection.
        val existingUser = users.find { user.id == it.id }
        existingUser?.let { _users.remove(it) }
        // Add the user.
        _users.add(user)
    }
}