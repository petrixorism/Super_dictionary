package uz.gita.superdictionary.ui.viewmodel

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.superdictionary.data.db.WordEntity

interface FavouritesViewModel {

    val cursorWordsLiveData: LiveData<Cursor>

    val failLiveData: LiveData<String>

    val updateWordLiveData: LiveData<Boolean>


    fun getSavedWords()

    fun updateWord(wordEntity: WordEntity)

}