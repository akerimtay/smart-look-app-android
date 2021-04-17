package com.akerimtay.smartwardrobe.outfit.data.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.outfit.model.Season
import java.util.*

@Entity(tableName = OutfitEntity.TABLE_NAME)
data class OutfitEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = ID)
    val id: Long,
    @ColumnInfo(name = NAME)
    val name: String,
    @ColumnInfo(name = GENDER)
    val gender: OutfitGender,
    @ColumnInfo(name = SEASON)
    val season: Season,
    @ColumnInfo(name = TEMPERATURE_FROM)
    val temperatureFrom: Int,
    @ColumnInfo(name = TEMPERATURE_TO)
    val temperatureTo: Int,
    @ColumnInfo(name = CREATED_DATE)
    val createdDate: Date,
) {
    companion object {
        const val TABLE_NAME = "outfits"
        const val ID = "id"
        const val NAME = "name"
        const val GENDER = "gender"
        const val SEASON = "season"
        const val TEMPERATURE_FROM = "temperature_from"
        const val TEMPERATURE_TO = "temperature_to"
        const val CREATED_DATE = "CREATED_DATE"
    }
}