package uz.gita.superdictionary.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.superdictionary.data.db.DictionaryDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @[Singleton Provides]
    fun provideDictionaryDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, DictionaryDatabase::class.java, "eng_dictionary.db")
            .createFromAsset("eng_dictionary.db")
            .allowMainThreadQueries()
            .build()

    @[Singleton Provides]
    fun provideWordDao(db: DictionaryDatabase) = db.getWordDao()


    @[Singleton Provides]
    fun provideHistoryDao(db: DictionaryDatabase) = db.getHistoryDao()

    @[Singleton Provides]
    fun provideAddedWordsDao(db: DictionaryDatabase) = db.getAddedWordDao()

}