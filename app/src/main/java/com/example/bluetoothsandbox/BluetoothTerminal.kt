package com.example.bluetoothsandbox

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context

class BluetoothTerminal(
    private val context: Context,
    private val permissionsManager: PermissionsManager
    ) {
    val monitor: BluetoothMonitor
    val scanner: BluetoothScanner
    val connectionManager: BluetoothConnectionManager
    private val adapter: BluetoothAdapter

    private companion object {
        val scanFilers = listOf<BluetoothScanner.Filter>(
//            BluetoothScanner.Filter.Name("vascovid-gatt-server")
        )
    }

    init {
        val manager: BluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        adapter = manager.adapter
        scanner = BluetoothScanner(context, permissionsManager, scanFilers, adapter)
        monitor = BluetoothMonitor(context,permissionsManager,  adapter)
        connectionManager = BluetoothConnectionManager(context)

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

    fun connectFirst() {
        permissionsManager.checkAndRequest(Manifest.permission.BLUETOOTH_CONNECT)
        val device = scanner.discoveredDevices.values.first()
        connectionManager.connect(device)
    }
}

fun BluetoothTerminal.click(number: Int) {
    when(number) {
        1 -> monitor.start()
        2 -> monitor.stop()
        3 -> scanner.scan()
        4 -> scanner.stop()
        5 -> connectFirst()
        6 -> connectionManager.disconnect()
        7 -> connectionManager.discoverServices()
        8 -> connectionManager.reconnect()
        else -> println("click button $number")
    }
}
