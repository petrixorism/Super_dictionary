package uz.gita.superdictionary.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "added_words")
data class AddedWordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "word")
    val word: String,
)
