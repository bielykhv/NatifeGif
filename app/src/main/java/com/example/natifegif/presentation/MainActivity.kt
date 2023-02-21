package com.example.natifegif.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.natifegif.R
import com.example.natifegif.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var resultLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
        supportFragmentManager.beginTransaction().replace(R.id.fContainer, GifListFragment()).commit()
    }
    private fun permissionListener() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            Toast.makeText(this, "Swipe up to load gifs", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission() {
        if (!isPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionListener()
            resultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
    private fun isPermissionGranted(p: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            p
        ) == PackageManager.PERMISSION_GRANTED
    }
}