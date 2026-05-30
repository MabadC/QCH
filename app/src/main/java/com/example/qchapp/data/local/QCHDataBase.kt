package com.example.qchapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalRecipeEntity::class],
    version = 1
)
abstract class QCHDatabase : RoomDatabase() {

    abstract fun localRecipeDao(): LocalRecipeDao
}