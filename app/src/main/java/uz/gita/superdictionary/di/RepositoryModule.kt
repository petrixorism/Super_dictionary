package uz.gita.superdictionary.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.superdictionary.domain.repository.HistoryRepository
import uz.gita.superdictionary.domain.repository.WordsRepository
import uz.gita.superdictionary.domain.repository.impl.AddedWordsRepository
import uz.gita.superdictionary.domain.repository.impl.HistoryRepositoryImpl
import uz.gita.superdictionary.domain.repository.impl.WordsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideWordsRepository(impl: WordsRepositoryImpl): WordsRepository


    @Binds
    @Singleton
    fun provideAddedWords(impl: AddedWordsRepository): uz.gita.superdictionary.domain.repository.AddedWordsRepository


    @Binds
    @Singleton
    fun provideHistory(impl: HistoryRepositoryImpl): HistoryRepository


}