package com.example.mviapp

import android.app.Application
import android.content.Context
import com.example.mviapp.data.AppDatabase
import com.example.mviapp.data.LocalPlantDataSource
import com.example.mviapp.data.PlantDataSource
import com.example.mviapp.data.PlantRepositoryImpl

class App : Application() {

    private var database: AppDatabase? = null
    @Volatile
    private var plantRepository: PlantRepositoryImpl? = null

    fun providePlantRepository(context: Context): PlantRepositoryImpl {
        synchronized(this) {
            return plantRepository ?: plantRepository ?: createPlantRepository(context)
        }
    }

    private fun createPlantRepository(context: Context): PlantRepositoryImpl {
        val newRepo = PlantRepositoryImpl(createLocalPlantDataSource(context))
        plantRepository = newRepo
        return newRepo
    }

    private fun createLocalPlantDataSource(context: Context): PlantDataSource {
        val database = database ?: AppDatabase.getInstance(context)
        return LocalPlantDataSource(database.plantDao())
    }

}