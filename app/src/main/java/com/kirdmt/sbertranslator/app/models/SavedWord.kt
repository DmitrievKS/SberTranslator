package com.kirdmt.sbertranslator.app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kirdmt.sbertranslator.app.util.Constants

@Entity(tableName = Constants.ROOM_DB_NAME)
data class SavedWord(@ColumnInfo(name = "native_word") val nativeWord: String, @ColumnInfo(name = "translated_word") val translatedWord: String) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

}
