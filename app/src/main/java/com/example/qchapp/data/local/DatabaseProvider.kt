package com.example.qchapp.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: QCHDatabase? = null

    fun getDatabase(context: Context): QCHDatabase {

        return INSTANCE ?: synchronized(this) {

            val instance = Room.databaseBuilder(
                context.applicationContext,
                QCHDatabase::class.java,
                "qch_database"
            ).build()

            INSTANCE = instance

            instance
        }
    }
}