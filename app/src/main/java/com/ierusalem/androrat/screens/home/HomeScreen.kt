package com.ierusalem.androrat.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ierusalem.androrat.ui.theme.AndroRATTheme

@Composable
fun HomeScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

        }
    )
}

@Preview
@Composable
fun HomeScreen_LightPreview() {
    AndroRATTheme(darkTheme = false) {
        HomeScreen()
    }
}

@Preview
@Composable
fun HomeScreen_DarkPreview() {
    AndroRATTheme(darkTheme = true) {
        HomeScreen()
    }
}