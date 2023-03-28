package com.azathoth.handlist.data.task

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class HandListDatabase : RoomDatabase() {
    abstract fun itemDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: HandListDatabase? = null

        fun getDatabase(context: Context): HandListDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, HandListDatabase::class.java, "task_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}