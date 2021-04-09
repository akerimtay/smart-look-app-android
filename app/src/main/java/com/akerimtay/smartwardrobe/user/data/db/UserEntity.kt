package com.akerimtay.smartwardrobe.user.data.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akerimtay.smartwardrobe.user.model.Gender
import java.util.*

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = ID)
    val id: String,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = GENDER)
    val gender: Gender,
    @ColumnInfo(name = EMAIL)
    val email: String,
    @ColumnInfo(name = BIRTH_DATE)
    val birthDate: Date?,
    @ColumnInfo(name = IMAGE)
    val imageUrl: String?
) {
    companion object {
        const val TABLE_NAME = "user"
        const val ID = "id"
        const val NAME = "name"
        const val GENDER = "gender"
        const val EMAIL = "email"
        const val BIRTH_DATE = "birth_date"
        const val IMAGE = "image"
    }
}