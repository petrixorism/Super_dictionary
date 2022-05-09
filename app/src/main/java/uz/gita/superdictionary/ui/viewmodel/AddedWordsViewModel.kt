package uz.gita.superdictionary.ui.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.superdictionary.data.db.AddedWordEntity
import uz.gita.superdictionary.data.db.WordEntity

interface AddedWordsViewModel {

    val getAllWordsLiveData: LiveData<List<AddedWordEntity>>
    val goToFlow: Flow<WordEntity>
    val editFlow: Flow<WordEntity>

    fun deleteWord(addedWordEntity: AddedWordEntity)

    fun editWord(addedWordEntity: AddedWordEntity)


    fun goToWord(addedWordEntity: AddedWordEntity)


}