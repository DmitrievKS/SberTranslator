package com.kirdmt.sbertranslator.app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kirdmt.sbertranslator.app.models.SavedWord
import com.kirdmt.sbertranslator.app.util.Constants

@Database(entities = arrayOf(SavedWord::class), version = 1)
public abstract class SavedWordRoomDatabase : RoomDatabase() {

    abstract fun savedWordDao(): SavedWordDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: SavedWordRoomDatabase? = null

        fun getDatabase(context: Context): SavedWordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavedWordRoomDatabase::class.java,
                    Constants.ROOM_DB_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}