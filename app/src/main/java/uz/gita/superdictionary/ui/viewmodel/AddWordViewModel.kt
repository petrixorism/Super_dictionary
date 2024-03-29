package uz.gita.superdictionary.ui.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.superdictionary.data.db.WordEntity

interface AddWordViewModel {

    val addWordLiveData: LiveData<Unit>
    val failLiveData: LiveData<String>

    fun addWord(wordEntity: WordEntity)

}