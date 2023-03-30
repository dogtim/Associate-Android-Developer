package com.example.associate.training

import android.app.Application

import androidx.test.core.app.ApplicationProvider
import com.example.associate.training.media.MediaViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TestApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        val testModule = module {
            factory {
                MediaViewModel(height = 480, width = 720)
            }
        }
        startKoin {
            //androidContext(ApplicationProvider.getApplicationContext())
            modules(testModule)
        }
    }
}