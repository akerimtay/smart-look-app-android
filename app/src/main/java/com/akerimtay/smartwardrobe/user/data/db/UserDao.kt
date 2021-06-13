package com.akerimtay.smartwardrobe.user.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(userEntity: UserEntity)

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.ID} = :id")
    fun getById(id: String): UserEntity?

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.ID} = :id")
    fun getByIdAsFlow(id: String): Flow<UserEntity?>

    @Query("DELETE FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.ID} = :id")
    fun deleteById(id: String)
}