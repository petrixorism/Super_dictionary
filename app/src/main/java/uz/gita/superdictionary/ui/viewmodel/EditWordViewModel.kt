package uz.gita.superdictionary.ui.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.superdictionary.data.db.WordEntity


interface EditWordViewModel {

    val editWordLiveData: LiveData<Unit>

    val failLiveData: LiveData<String>

    fun editWord(wordEntity: WordEntity)

}