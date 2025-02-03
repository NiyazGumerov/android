package ru.itis.homework5

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.itis.homework5.ui.theme.HomeWork5Theme

private const val FAILURES_COUNT_KEY = "FAILURES_COUNT"

class MainActivity : ComponentActivity() {

    private var failuresCount = 0

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            (application as? App)?.getPreferences()?.edit()?.let {
                it.putInt(FAILURES_COUNT_KEY, failuresCount + 1)
                it.commit()
            }
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (failuresCount >= 2) {
                showPermissionRationale()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showPermissionRationale() {
        AlertDialog.Builder(this).setTitle(R.string.request_permission_notifications_title)
            .setMessage(R.string.request_permission_notifications_message)
            .setPositiveButton(R.string.request_permission_notifications_positive_button) { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            }.setNegativeButton(R.string.request_permission_notifications_negative_button, null)
            .show()
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as? App)?.getPreferences()?.let { prefs ->
            failuresCount = prefs.getInt(FAILURES_COUNT_KEY, 0)
        }

        enableEdgeToEdge()
        setContent {
            HomeWork5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                    MyScreen()
                }
            }
        }
        requestNotificationPermission()
    }
}