package com.ierusalem.androrat.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ierusalem.androrat.screens.home.HomeScreen
import com.ierusalem.androrat.ui.theme.AndroRATTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroRATTheme {
                HomeScreen()
            }
        }
    }
}
