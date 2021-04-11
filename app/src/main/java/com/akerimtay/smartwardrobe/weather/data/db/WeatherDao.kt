package com.akerimtay.smartwardrobe.weather.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(weatherEntity: WeatherEntity)

    @Transaction
    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.ID} = :id")
    fun getByIdAsFlow(id: Int): LiveData<WeatherEntity?>

    @Transaction
    @Query("DELETE FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.ID} = :id")
    fun deleteById(id: Int)
}