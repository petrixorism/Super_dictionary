package uz.gita.superdictionary.ui.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.superdictionary.data.db.WordEntity

interface WordViewModel {

    val updateLiveData: LiveData<Boolean>

    fun updateWord(wordEntity: WordEntity)

}