package uz.gita.superdictionary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WordEntity::class, HistoryEntity::class],
    version = 1
)

abstract class DictionaryDatabase : RoomDatabase() {

    abstract fun getWordDao(): WordDao

    abstract fun getHistoryDao(): HistoryDao

}