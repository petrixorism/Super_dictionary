package uz.gita.superdictionary.ui.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import uz.gita.superdictionary.data.db.AddedWordEntity
import uz.gita.superdictionary.data.db.AddedWordsDao
import uz.gita.superdictionary.data.db.WordDao
import uz.gita.superdictionary.data.db.WordEntity
import uz.gita.superdictionary.ui.viewmodel.AddedWordsViewModel
import javax.inject.Inject

@HiltViewModel
class AddedWordsViewModelImpl @Inject constructor(
    private val addedWordsDao: AddedWordsDao,
    private val wordDao: WordDao
) : ViewModel(),
    AddedWordsViewModel {


    override val getAllWordsLiveData = addedWordsDao.getAllAddedWords()

    private val editChannel = Channel<WordEntity>()
    private val goToChannel = Channel<WordEntity>()

    override val goToFlow = goToChannel.receiveAsFlow()
    override val editFlow = editChannel.receiveAsFlow()

    override fun deleteWord(addedWordEntity: AddedWordEntity) {

        val word = wordDao.getOneWord(addedWordEntity.word)
        addedWordsDao.delete(addedWordEntity)
        wordDao.deleteWord(word!!)

    }

    override fun editWord(addedWordEntity: AddedWordEntity) {

        viewModelScope.launch {
            val word = wordDao.getOneWord(addedWordEntity.word)
            editChannel.send(word!!)
        }
    }

    override fun goToWord(addedWordEntity: AddedWordEntity) {

        viewModelScope.launch {
            val word = wordDao.getOneWord(addedWordEntity.word)
            goToChannel.send(word!!)
        }


    }

}