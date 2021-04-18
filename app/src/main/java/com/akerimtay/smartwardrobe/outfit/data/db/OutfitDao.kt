package com.akerimtay.smartwardrobe.outfit.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender

@Dao
interface OutfitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(outfits: List<OutfitEntity>)

    @Query("SELECT * FROM outfits WHERE gender = :gender")
    fun getByGender(gender: OutfitGender): DataSource.Factory<Int, OutfitEntity>

    @Transaction
    @Query("DELETE FROM ${OutfitEntity.TABLE_NAME}")
    fun deleteAll()
}