package com.akerimtay.smartwardrobe.favorites.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: FavoriteEntity)

    @Transaction
    @Query(
        """
        SELECT * FROM ${FavoriteEntity.TABLE_NAME} 
        WHERE ${FavoriteEntity.USER_ID} = :userId
        GROUP BY ${FavoriteEntity.OUTFIT_ID}
        """
    )
    fun getAllByUserIdAsFlow(userId: String): Flow<List<FavoriteDetailEntity>>

    @Query("DELETE FROM ${FavoriteEntity.TABLE_NAME} WHERE ${FavoriteEntity.ID} = :id")
    fun deleteById(id: Long)

    @Query(
        """
        DELETE FROM ${FavoriteEntity.TABLE_NAME} 
        WHERE ${FavoriteEntity.USER_ID} = :userId AND ${FavoriteEntity.OUTFIT_ID} = :outfitId
        """
    )
    fun deleteByOutfitId(userId: String, outfitId: Long)

    @Query(
        """
        SELECT COUNT(*) FROM ${FavoriteEntity.TABLE_NAME} 
        WHERE ${FavoriteEntity.USER_ID} = :userId AND ${FavoriteEntity.OUTFIT_ID} = :outfitId
        """
    )
    fun getCountAsFlow(userId: String, outfitId: Long): Flow<Long>
}