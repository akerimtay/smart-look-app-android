package com.akerimtay.smartwardrobe.favorites.data.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.akerimtay.smartwardrobe.outfit.data.db.OutfitEntity
import com.akerimtay.smartwardrobe.user.data.db.UserEntity

@Entity(
    tableName = FavoriteEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserEntity.ID],
            childColumns = [FavoriteEntity.USER_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = OutfitEntity::class,
            parentColumns = [OutfitEntity.ID],
            childColumns = [FavoriteEntity.OUTFIT_ID],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class FavoriteEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = ID)
    val id: Long,
    @ColumnInfo(name = USER_ID, index = true)
    val userId: String,
    @ColumnInfo(name = OUTFIT_ID, index = true)
    val outfitId: Long
) {
    companion object {
        const val TABLE_NAME = "favorites"
        const val ID = "id"
        const val USER_ID = "userId"
        const val OUTFIT_ID = "outfitId"
    }
}