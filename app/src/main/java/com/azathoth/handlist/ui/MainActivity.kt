package com.azathoth.handlist.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandListApp()
        }
    }

    override fun onDestroy() {
        val prefs = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        super.onDestroy()

    }
}