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
        (findViewById<Button>(R.id.bt_7)).setOnClickListener { terminal.click(7) }
        (findViewById<Button>(R.id.bt_8)).setOnClickListener { terminal.click(8) }
        (findViewById<Button>(R.id.bt_9)).setOnClickListener { terminal.click(9) }
        (findViewById<Button>(R.id.bt_10)).setOnClickListener { terminal.click(10) }
        (findViewById<Button>(R.id.bt_11)).setOnClickListener { terminal.click(11) }
    }
}