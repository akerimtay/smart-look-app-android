package com.akerimtay.smartwardrobe.user.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(userEntity: UserEntity)

    @Transaction
    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.ID} = :id")
    fun getById(id: String): UserEntity?

    @Transaction
    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.ID} = :id")
    fun getByIdAsFlow(id: String): LiveData<UserEntity?>

    @Transaction
    @Query("DELETE FROM ${UserEntity.TABLE_NAME} WHERE ${UserEntity.ID} = :id")
    fun deleteById(id: String)
}