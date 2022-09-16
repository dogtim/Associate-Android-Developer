package com.example.associate.training

import android.app.Application
import com.example.associate.training.busschedule.database.AppDatabase
import com.example.associate.training.inventory.data.ItemRoomDatabase
import com.example.associate.training.koin.HelloRepository
import com.example.associate.training.koin.HelloRepositoryImpl
import com.example.associate.training.koin.MyViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {

    // single instance of HelloRepository
    single<HelloRepository> { HelloRepositoryImpl() }

    // MyViewModel ViewModel
    viewModel { MyViewModel(get()) }
}

class AssociateApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
    val inventoryDatabase: ItemRoomDatabase by lazy { ItemRoomDatabase.getDatabase(this) }
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidContext(this@AssociateApplication)
            modules(appModule)
        }
    }
}
