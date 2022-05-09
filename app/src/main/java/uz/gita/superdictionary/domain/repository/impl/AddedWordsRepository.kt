package uz.gita.superdictionary.domain.repository.impl


import uz.gita.superdictionary.data.db.AddedWordsDao
import uz.gita.superdictionary.domain.repository.AddedWordsRepository
import javax.inject.Inject

class AddedWordsRepository @Inject constructor(private val dao: AddedWordsDao) :
    AddedWordsRepository {



}