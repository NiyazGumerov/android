package ru.itis.homework4

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.homework4.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var CHANNEL_ID = "Homework4"
    private var themeResId: Int = R.style.HomeWork4

    private val viewBinding: ActivityMainBinding by viewBinding(
        ActivityMainBinding::bind
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as App
        themeResId = app.getThemeResId()
        setTheme(themeResId)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        requestNotificationPermission()

        if (intent.getBooleanExtra("fromNotification", false)) {
            showToast("Вы перешли из уведомления")
        }

        with(viewBinding) {

            val colors = listOf(
                R.color.red to R.style.HomeWork4_Red,
                R.color.green to R.style.HomeWork4_Green,
                R.color.blue to R.style.HomeWork4_Blue
            )

            val adapter = ThemeAdapter(colors) { selectedThemeResId ->
                setMyTheme(selectedThemeResId)
            }

            rvThemeColors.adapter = adapter

            cvRecyclerView.setOnClickListener{
                if (rvThemeColors.visibility == View.VISIBLE) {
                    rvThemeColors.visibility = View.GONE
                } else {
                    rvThemeColors.visibility = View.VISIBLE
                }
            }
            btnResetColor.setOnClickListener {
                setMyTheme(R.style.HomeWork4)
            }
            btnCloseImage.setOnClickListener {
                ivAvatar.visibility = View.INVISIBLE
                btnCloseImage.visibility = View.INVISIBLE
            }
            cvAvatar.setOnClickListener {
                if (ivAvatar.visibility == View.VISIBLE) {
                    showToast("Изображение уже открыто")
                } else {
                    ivAvatar.visibility = View.VISIBLE
                    btnCloseImage.visibility = View.VISIBLE
                }
            }
            var notificationPriority = spinnerNotificationPriority
            var notificationTitle = tietTitle
            var notificationMessage = tietMessage
            createNotificationChannel()

            btnShowNotification.setOnClickListener {
                val title = notificationTitle.text.toString()
                val message = notificationMessage.text.toString()
                if (title.isEmpty() && message.isEmpty()) {
                    showToast("Заполните заголовок и текст уведомления")
                } else if (title.isEmpty()) {
                    showToast("Заполните заголовок уведомления")
                } else if (message.isEmpty()) {
                    showToast("Заполните текст уведомления")
                } else {
                    sendNotification(
                        notificationPriority.selectedItemPosition,
                        message,
                        title
                    )
                }
            }

        }

    }

    fun sendNotification(priority: Int, message: String, title: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission()
        } else {
            val notificationPriority = when (priority) {
                0 -> NotificationCompat.PRIORITY_LOW
                1 -> NotificationCompat.PRIORITY_DEFAULT
                2 -> NotificationCompat.PRIORITY_HIGH
                3 -> NotificationCompat.PRIORITY_MAX
                else -> NotificationCompat.PRIORITY_DEFAULT
            }

            val intent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra("fromNotification", true)
            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_action_1)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(notificationPriority)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
            with(NotificationManagerCompat.from(this)) {
                notify(Random.nextInt(), builder.build())
            }
        }
    }


    private fun setMyTheme(themeResId: Int) {
        val app = application as App
        app.setThemeResId(themeResId)
        recreate()
    }


    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 0)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showToast(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

}