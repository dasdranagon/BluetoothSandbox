package com.example.bluetoothsandbox

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


interface PermissionsManager {
    fun checkAndRequest(permission: String)
}

class MainPermissionsManager(private val activity: AppCompatActivity) : PermissionsManager {
    private companion object {
        const val REQUEST_CODE = 32131
    }
    override fun checkAndRequest(permission: String) {
        if (ContextCompat.checkSelfPermission(
                activity,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, permission
                )
            ) {
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    REQUEST_CODE
                )
            }
        }
    }
}