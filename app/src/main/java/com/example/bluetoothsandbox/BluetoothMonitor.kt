package com.example.bluetoothsandbox

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class BluetoothMonitor(
    private val context: Context,
    private val permissionsManager: PermissionsManager,
    val adapter: BluetoothAdapter) {

    @SuppressLint("MissingPermission")
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            println("RECEIVED ACTION:  ${intent?.action} ")
            val device = intent?.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
            permissionsManager.checkAndRequest(Manifest.permission.BLUETOOTH_CONNECT)

            println("DEVICE: $device / STATE: ${
                when(device?.bondState) {
                    BluetoothDevice.BOND_NONE -> "BOND_NONE"
                    BluetoothDevice.BOND_BONDED -> "BOND_BONDED"
                    BluetoothDevice.BOND_BONDING -> "BOND_BONDING"
                    else -> "Unknown ${device?.bondState}"
                }
            }")
        }
    }

    fun start() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        intentFilter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST)
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        context.registerReceiver(
            broadcastReceiver,
            intentFilter
        )
    }

    fun stop() {
        context.unregisterReceiver(broadcastReceiver)
    }
}