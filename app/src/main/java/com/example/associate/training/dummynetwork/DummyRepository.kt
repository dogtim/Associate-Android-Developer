package com.example.associate.training.dummynetwork

import com.example.associate.training.dummynetwork.data.DummyUsers
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.URL

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

// Create the repository to fetch the dummy api, and build the response data structure
// TODO: Implement the coroutine flow as https://github.com/TouhidApps/Kotlin-Coroutine-Example
class DummyRepository() {
    private val loginUrl = "https://dummyapi.io/data/v1/user?limit=10"
    private val appID = "62aaf8e92897f92a5305562c"
    // Function that makes the network request, blocking the current thread
    fun makeLoginRequest(): Result<DummyUsers> {
        try {
            val url = URL(loginUrl)
            (url.openConnection() as? HttpURLConnection)?.run {
                requestMethod = "GET"
                setRequestProperty("app-id", appID)
                doInput = true
                connect()
                val inputAsString = inputStream.bufferedReader().use { it.readText() }
                inputStream.close()
                return Result.Success(Gson().fromJson(inputAsString, DummyUsers::class.java))
            }

        } catch (e: HttpException) {
            e.printStackTrace()
        }
        return Result.Error(Exception("Cannot open HttpURLConnection"))
    }
}