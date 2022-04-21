package com.example.bluetoothsandbox

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context

class BluetoothScanner(
    private val context: Context,
    private val permissionsManager: PermissionsManager,
    adapter: BluetoothAdapter
) {
    private val scanner: BluetoothLeScanner = adapter.bluetoothLeScanner

    var discoveredDevices = mutableMapOf<String, BluetoothDevice>()
    var onUpdated: ((List<BluetoothDevice>) -> Unit)? = null

    @SuppressLint("MissingPermission")
    fun scan() {
        println(" SCANNING ...")
        permissionsManager.checkAndRequest(Manifest.permission.BLUETOOTH_SCAN)
        scanner.startScan(scanCallback)

    }

    private val scanCallback = object : ScanCallback() {

        override fun onScanFailed(errorCode: Int) {
            println("SCAN. Failed with error $errorCode")
        }

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if (callbackType == ScanSettings.CALLBACK_TYPE_MATCH_LOST) return
//            println("SCAN. Result: $result")
            handleScanResult(result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>) {
//            println("SCAN. Batch Result: $results")
            results.forEach {
                handleScanResult(it)
            }
        }

        private fun handleScanResult(scanResult: ScanResult) {
//            println("Discovered device: ${scanResult.device}")

            discoveredDevices[scanResult.device.address] = scanResult.device
            onUpdated?.invoke(discoveredDevices.values.toList())
        }
    }
}