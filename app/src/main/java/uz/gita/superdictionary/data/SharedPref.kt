package uz.gita.superdictionary.data

import android.content.Context

class SharedPref(context: Context) {

    companion object {

        private var instance: SharedPref? = null

        fun init(context: Context) {
            instance = SharedPref(context)
        }

        fun getInstance(): SharedPref = instance!!

    }

    private val pref = context.getSharedPreferences("USER_DATA", Context.MODE_PRIVATE)



    var isDayMode: Boolean
        set(value) = pref.edit().putBoolean("IS_DAY_MODE", value).apply()
        get() = pref.getBoolean("IS_DAY_MODE", true)

}