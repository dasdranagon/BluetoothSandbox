package com.example.bluetoothsandbox

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.*
import android.content.Context

class BluetoothScanner(
    private val context: Context,
    private val permissionsManager: PermissionsManager,
    private val filters: List<Filter>,
    adapter: BluetoothAdapter
) {
    private companion object {
        val settings = ScanSettings.Builder().build()
    }
    sealed class Filter {
        data class Name(val value: String): Filter()
    }

    private val scanner: BluetoothLeScanner = adapter.bluetoothLeScanner

    var discoveredDevices = mutableMapOf<String, BluetoothDevice>()
    var onUpdated: ((List<BluetoothDevice>) -> Unit)? = null

    @SuppressLint("MissingPermission")
    fun scan() {
        println(" SCANNING ...")
        permissionsManager.checkAndRequest(Manifest.permission.BLUETOOTH_SCAN)

        val filters = filters.map {
            when(it) {
                is Filter.Name -> ScanFilter.Builder().setDeviceName(it.value).build()
            }
        }
        scanner.startScan(filters, settings, scanCallback)
    }

    @SuppressLint("MissingPermission")
    fun stop() {
        scanner.stopScan(scanCallback)
    }

    private val scanCallback = object : ScanCallback() {

        override fun onScanFailed(errorCode: Int) {
            println("SCAN. Failed with error $errorCode")
        }

        override fun onScanResult(callbackType: Int, result: ScanResult) {
            if (callbackType == ScanSettings.CALLBACK_TYPE_MATCH_LOST) return
            handleScanResult(result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>) {
            results.forEach {
                handleScanResult(it)
            }
        }

        private fun handleScanResult(scanResult: ScanResult) {
            discoveredDevices[scanResult.device.address] = scanResult.device
            onUpdated?.invoke(discoveredDevices.values.toList())
        }
    }
}