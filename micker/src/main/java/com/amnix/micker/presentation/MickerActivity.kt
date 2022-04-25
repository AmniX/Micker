package com.amnix.micker.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.amnix.micker.presentation.ui.composables.screen.MickerScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MickerActivity : ComponentActivity() {

    private val permissionRequester = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGiven ->
        if (isGiven) {
            setContent {
                MickerScreen()
            }
        } else {
            Toast.makeText(this, "Permission is Required.", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            setContent {
                MickerScreen()
            }
        } else {
            permissionRequester.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
}