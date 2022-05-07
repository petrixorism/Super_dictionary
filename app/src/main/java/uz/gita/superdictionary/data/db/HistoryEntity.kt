package uz.gita.superdictionary.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "word")
    val word: String,

    @ColumnInfo(name = "time")
    val time: String = System.currentTimeMillis().toString()

)
