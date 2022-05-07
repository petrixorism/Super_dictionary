package uz.gita.superdictionary.ui.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.superdictionary.data.db.WordEntity

interface HomeViewModel {

    val cursorWordsLiveData: LiveData<Cursor>

    val failLiveData: LiveData<String>

    val updateWordLiveData: LiveData<Unit>


    fun getAllWords()

    fun searchWord(query: String)

    fun updateWord(wordEntity: WordEntity)


}