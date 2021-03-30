package com.akerimtay.smartwardrobe.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akerimtay.smartwardrobe.user.data.db.UserDao
import com.akerimtay.smartwardrobe.user.data.db.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}