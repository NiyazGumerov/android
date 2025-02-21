package ru.itis.homework6

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import ru.itis.homework6.di.ServiceLocator

const val USER_ID_KEY = "USER_ID"
private const val PREF_NAME = "SAMPLE_SP"

class App : Application() {

    private lateinit var sharedPref: SharedPreferences

    private val serviceLocator = ServiceLocator

    override fun onCreate() {
        sharedPref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        super.onCreate()
        serviceLocator.initDataLayerDependencies(this, sharedPref)
    }

    fun getPreferences(): SharedPreferences {
        return sharedPref
    }
}