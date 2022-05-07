package uz.gita.superdictionary.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import uz.gita.superdictionary.data.db.DictionaryDatabase

@HiltAndroidApp
class DictionaryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
            private set
    }

}