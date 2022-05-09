package uz.gita.superdictionary.data.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface AddedWordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(addedWordEntity: AddedWordEntity)

    @Delete
    fun delete(addedWordEntity: AddedWordEntity)

    @Update
    fun update(addedWordEntity: AddedWordEntity)

    @Query("SELECT * FROM added_words ORDER BY word")
    fun getAllAddedWords(): LiveData<List<AddedWordEntity>>

}