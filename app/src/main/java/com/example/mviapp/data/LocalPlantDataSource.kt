package com.example.mviapp.data

class LocalPlantDataSource(
    private val plantDao: PlantDao
) : PlantDataSource {
    override fun getPlants(): List<Plant> {
        return plantDao.getPlants()
    }

}