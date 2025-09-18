package com.ierusalem.androrat.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    message: String = stringResource(R.string.loading),
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Preview
@Composable
fun LoadingScreen_LightPreview(){
    AndroRATTheme(isDarkTheme = false) {
        LoadingScreen()
    }
}

@Preview
@Composable
fun LoadingScreen_DarkPreview(){
    AndroRATTheme(isDarkTheme = true) {
        LoadingScreen()
    }
}