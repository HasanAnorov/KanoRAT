package com.ierusalem.androrat.features.images.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ierusalem.androrat.features.home.domain.Image
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme

@Composable
fun ImagesScreen(
    onUploadClick: () -> Unit,
    images: List<Image>
) {
    Log.d("ahi3646_hh", "ImagesScreen: $images ")
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
        Button(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
                .fillMaxWidth(),
            onClick = onUploadClick,
            content = {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Upload images",
                    color = Color.Black
                )
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .background(MaterialTheme.colorScheme.surface),
            content = {
                items(images) { image ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = image.uri,
                            contentDescription = null
                        )
                        Text(text = "Image Display Name - ${image.displayName}")
                    }
                }
            }
        )
    }
}

@Composable
@Preview
fun Preview_ImagesScreen() {
    AndroRATTheme(isDarkTheme = false) {
        ImagesScreen(
            onUploadClick = {},
            images = emptyList()
        )
    }
}

@Composable
@Preview
fun Preview_Dark_ImagesScreen() {
    AndroRATTheme(isDarkTheme = true) {
        ImagesScreen(
            onUploadClick = {},
            images = emptyList()
        )
    }
}