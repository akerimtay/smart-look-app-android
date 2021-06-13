package com.akerimtay.smartwardrobe.articles.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(articles: List<ArticleEntity>)

    @Query("SELECT * FROM ${ArticleEntity.TABLE_NAME}")
    fun getAllAsLiveData(): Flow<List<ArticleEntity>>

    @Transaction
    @Query("DELETE FROM ${ArticleEntity.TABLE_NAME}")
    fun deleteAll()
}