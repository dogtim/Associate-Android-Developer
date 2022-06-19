package com.example.associate.training.dummy

import com.example.associate.training.dummy.data.DummyUsers
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.URL

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

// Create the repository to fetch the dummy api, and build the response data structure
// TODO: Implement the coroutine flow as https://github.com/TouhidApps/Kotlin-Coroutine-Example
//
class DummyRepository() {
    private val loginUrl = "https://dummyapi.io/data/v1/user?limit=10"
    private val appID = "62adc6ad2aa7570c08535305"
    // Function that makes the network request, blocking the current thread
    suspend fun makeLoginRequest(): Result<DummyUsers> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(loginUrl)
                (url.openConnection() as? HttpURLConnection)?.run {
                    requestMethod = "GET"
                    setRequestProperty("app-id", appID)
                    doInput = true
                    connect()
                    val inputAsString = inputStream.bufferedReader().use { it.readText() }
                    inputStream.close()
                    return@withContext Result.Success(Gson().fromJson(inputAsString, DummyUsers::class.java))
                }

            } catch (e: HttpException) {
                e.printStackTrace()
            }
            return@withContext Result.Error(Exception("Cannot open HttpURLConnection"))
        }

    }
}