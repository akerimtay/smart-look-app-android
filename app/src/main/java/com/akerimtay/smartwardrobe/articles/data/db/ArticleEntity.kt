package com.akerimtay.smartwardrobe.articles.data.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akerimtay.smartwardrobe.user.model.Gender

@Entity(tableName = ArticleEntity.TABLE_NAME)
data class ArticleEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = ID)
    val id: Long,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = DESCRIPTION)
    val description: String,
    @ColumnInfo(name = GENDER)
    val gender: Gender,
    @ColumnInfo(name = IMAGE_URL)
    val imageUrl: String,
    @ColumnInfo(name = SOURCE_URL)
    val sourceUrl: String,
) {
    companion object {
        const val TABLE_NAME = "articles"
        const val ID = "id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val GENDER = "gender"
        const val IMAGE_URL = "imageUrl"
        const val SOURCE_URL = "sourceUrl"
    }
}