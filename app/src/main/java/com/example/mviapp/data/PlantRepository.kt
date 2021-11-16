package com.example.mviapp.data

interface PlantRepository {
    fun getPlants(): List<Plant>
}