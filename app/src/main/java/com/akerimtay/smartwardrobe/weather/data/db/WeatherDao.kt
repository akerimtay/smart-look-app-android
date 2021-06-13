package com.akerimtay.smartwardrobe.weather.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.ID} = :id")
    fun getByIdAsFlow(id: Int): Flow<WeatherEntity?>

    @Query("DELETE FROM ${WeatherEntity.TABLE_NAME} WHERE ${WeatherEntity.ID} = :id")
    fun deleteById(id: Int)
}