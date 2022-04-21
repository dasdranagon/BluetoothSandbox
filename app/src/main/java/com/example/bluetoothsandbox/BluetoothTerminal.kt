package com.example.bluetoothsandbox

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context

class BluetoothTerminal(context: Context, private val permissionsManager: PermissionsManager, ) {
    val monitor: BluetoothMonitor
    val scanner: BluetoothScanner
    private val adapter: BluetoothAdapter

    init {
        val manager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        adapter = manager.adapter
        scanner = BluetoothScanner(context, permissionsManager, adapter)
        monitor = BluetoothMonitor(context,permissionsManager,  adapter)

        permissionsManager.checkAndRequest(Manifest.permission.ACCESS_FINE_LOCATION)

        observe()
    }

    @SuppressLint("MissingPermission")
    private fun observe() {
        permissionsManager.checkAndRequest(Manifest.permission.BLUETOOTH_CONNECT)
        scanner.onUpdated = {
            println(" DISCOVERED: ${it.mapNotNull { it.name }}")
        }
    }
}

fun BluetoothTerminal.click(number: Int) {
    when(number) {
        1 -> monitor.start()
        2 -> monitor.stop()
        3 -> scanner.scan()

        else -> println("click button $number")
    }
}
