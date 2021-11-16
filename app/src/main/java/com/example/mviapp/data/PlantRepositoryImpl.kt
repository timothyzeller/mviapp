package com.example.mviapp.data

class PlantRepositoryImpl constructor(
    private val localPlantDataSource: PlantDataSource
) : PlantRepository {

    override fun getPlants() = localPlantDataSource.getPlants()

}