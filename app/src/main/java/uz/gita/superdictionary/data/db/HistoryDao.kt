package uz.gita.superdictionary.data.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(historyEntity: HistoryEntity)

    @Delete
    fun deleteWord(historyEntity: HistoryEntity)


    @Query("SELECT * from history ORDER BY time DESC")
    fun getWordsHistory(): LiveData<List<HistoryEntity>>


    @Query("SELECT * from history ORDER BY time DESC")
    fun getWordsHistoryList(): List<HistoryEntity>


}