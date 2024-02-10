package com.ierusalem.androrat.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.ierusalem.androrat.ui.components.CommonAndroRatButton
import com.ierusalem.androrat.ui.theme.AndroRATTheme
import com.ierusalem.androrat.ui.theme.dimens

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            CommonAndroRatButton(
                onClick = { },
                text = "Take a screenshot",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.spacing16,
                        end = MaterialTheme.dimens.spacing16,
                        bottom = MaterialTheme.dimens.spacing38
                    )
                    .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                    .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
            )
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