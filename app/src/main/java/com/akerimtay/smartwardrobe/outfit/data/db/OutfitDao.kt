package com.akerimtay.smartwardrobe.outfit.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.outfit.model.Season
import kotlinx.coroutines.flow.Flow

@Dao
interface OutfitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(outfits: List<OutfitEntity>)

    @Query("SELECT * FROM ${OutfitEntity.TABLE_NAME} WHERE ${OutfitEntity.GENDER} = :gender")
    fun getByGender(gender: OutfitGender): DataSource.Factory<Int, OutfitEntity>

    @Transaction
    @Query("DELETE FROM ${OutfitEntity.TABLE_NAME}")
    fun deleteAll()

    @Query("SELECT * FROM ${OutfitEntity.TABLE_NAME} WHERE ${OutfitEntity.ID} = :id")
    fun getByIdAsFlow(id: Long): Flow<OutfitEntity?>

    @Query(
        """
        SELECT * FROM ${OutfitEntity.TABLE_NAME} 
        WHERE ${OutfitEntity.ID} != :id AND 
        ${OutfitEntity.SEASON} = :season AND 
        ${OutfitEntity.GENDER} = :gender
        LIMIT :limit
        """
    )
    fun getSimilarAsFlow(
        id: Long,
        season: Season,
        gender: OutfitGender,
        limit: Long
    ): Flow<List<OutfitEntity>>
}