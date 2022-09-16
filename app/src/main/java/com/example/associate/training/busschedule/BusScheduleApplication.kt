package com.example.associate.training.busschedule
/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application
import com.example.associate.training.busschedule.database.AppDatabase
import com.example.associate.training.inventory.data.ItemRoomDatabase
import com.example.associate.training.koin.HelloRepository
import com.example.associate.training.koin.HelloRepositoryImpl
import com.example.associate.training.koin.MyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {

    // single instance of HelloRepository
    single<HelloRepository> { HelloRepositoryImpl() }

    // MyViewModel ViewModel
    viewModel { MyViewModel(get()) }
}

class BusScheduleApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val inventoryDatabase: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidContext(this@BusScheduleApplication)
            modules(appModule)
        }
    }
}
