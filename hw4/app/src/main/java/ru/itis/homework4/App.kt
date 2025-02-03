package ru.itis.homework4

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class App : Application() {
    private var themeResId: Int = R.style.HomeWork4

    fun getThemeResId(): Int {
        return themeResId
    }

    fun setThemeResId(themeResId: Int) {
        this.themeResId = themeResId
    }



}