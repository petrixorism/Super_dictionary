package uz.gita.superdictionary.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [WordEntity::class],
    version = 1
)

abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao

}