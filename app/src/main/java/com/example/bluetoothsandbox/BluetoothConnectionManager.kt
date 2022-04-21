package com.example.bluetoothsandbox

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context

class BluetoothConnectionManager(private val context: Context) {

    private var gatt: BluetoothGatt? = null

    @SuppressLint("MissingPermission")
    fun connect(device: BluetoothDevice) {
        gatt = device.connectGatt(context, false, callback, BluetoothDevice.TRANSPORT_BREDR)
    }

    @SuppressLint("MissingPermission")
    fun reconnect() {
        gatt?.connect()
    }

    @SuppressLint("MissingPermission")
    fun disconnect() {
        gatt?.disconnect()
    }

    @SuppressLint("MissingPermission")
    fun discoverServices() {
        gatt?.discoverServices()
    }

    private val callback = object : BluetoothGattCallback() {

        private fun mapStatus(code: Int): String = when(code) {
            BluetoothGatt.GATT_SUCCESS -> "Success"
            else -> "Unknown(code: $code)"
        }

        private fun mapState(code: Int): String = when(code) {
            BluetoothProfile.STATE_DISCONNECTED -> "Disconnected"
            BluetoothProfile.STATE_CONNECTED -> "Connected"
            BluetoothProfile.STATE_CONNECTING -> "Connecting"
            else -> "Unknown(code: $code)"
        }

        override fun onReadRemoteRssi(gatt: BluetoothGatt?, rssi: Int, status: Int) {
            println("NEW RSSI :: rssi $rssi :: status ${mapStatus(status)}")
        }

        override fun onMtuChanged(gatt: BluetoothGatt?, mtu: Int, status: Int) {
            println("NEW MTU :: mtu $mtu :: status ${mapStatus(status)}")
        }

        override fun onCharacteristicRead(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            println("⬅️ Characteristic READ :: characteristic $characteristic :: status ${mapStatus(status)}")
        }

        override fun onCharacteristicWrite(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?, status: Int) {
            println("➡️ Characteristic WRITE :: characteristic $characteristic :: status ${mapStatus(status)}")
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            println("⬅️ Service DISCOVERED  :: status ${mapStatus(status)}")
        }

        override fun onDescriptorWrite(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            println("➡️ Descriptor WRITE :: descriptor $descriptor :: status ${mapStatus(status)}")
        }

        override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
            println("↔️ Characteristic CHANGED :: characteristic $characteristic")
        }

        override fun onDescriptorRead(gatt: BluetoothGatt?, descriptor: BluetoothGattDescriptor?, status: Int) {
            println("⬅️ Descriptor READ :: descriptor $descriptor :: status ${mapStatus(status)}")
        }

        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            println("⚡️ Connection State CHANGED :: status ${mapStatus(status)} :: newState ${mapState(newState)}️")
        }
    }
}