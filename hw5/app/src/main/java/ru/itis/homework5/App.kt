package ru.itis.homework5;

import android.app.Application;
import android.content.Context
import android.content.SharedPreferences

class App: Application() {

    private val PREF_NAME = "SAMPLE_SP"
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(){
        sharedPref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        super.onCreate()
    }

    fun getPreferences(): SharedPreferences {
        return sharedPref
    }
}