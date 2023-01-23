package com.example.projets9.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projets9.R
import com.example.projets9.api.APIActivity
import com.example.projets9.databinding.ActivityMainBinding
import com.example.projets9.device.LocalPreferences
import com.example.projets9.scan.ScannerActivity
import com.example.projets9.scan.ScannerActivity.Companion.getStartIntent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.commander.setOnClickListener {
            startActivity(APIActivity.getStartIntent(this, LocalPreferences.getInstance(this).lastConnectedDeviceName()))
        }

        binding.scanner.setOnClickListener {
            startActivity(ScannerActivity.getStartIntent(this))
        }
    }
}