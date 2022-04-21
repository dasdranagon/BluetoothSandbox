package com.example.bluetoothsandbox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var terminal: BluetoothTerminal
    private val permissionsManager: PermissionsManager = MainPermissionsManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        terminal = BluetoothTerminal(applicationContext, permissionsManager)

        (findViewById<Button>(R.id.bt_1)).setOnClickListener { terminal.click(1) }
        (findViewById<Button>(R.id.bt_2)).setOnClickListener { terminal.click(2) }
        (findViewById<Button>(R.id.bt_3)).setOnClickListener { terminal.click(3) }
        (findViewById<Button>(R.id.bt_4)).setOnClickListener { terminal.click(4) }
        (findViewById<Button>(R.id.bt_5)).setOnClickListener { terminal.click(5) }
        (findViewById<Button>(R.id.bt_6)).setOnClickListener { terminal.click(6) }
    }
}