package com.amnix.micker

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.amnix.micker.presentation.MickerActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MickerActivity::class.java))
        finish()
    }
}