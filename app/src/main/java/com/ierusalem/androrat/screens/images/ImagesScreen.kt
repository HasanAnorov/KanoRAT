package com.ierusalem.androrat.screens.images

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ierusalem.androrat.screens.home.Image
import com.ierusalem.androrat.ui.theme.AndroRATTheme

@Composable
fun ImagesScreen(images: List<Image>) {
    Log.d("ahi3646_hh", "ImagesScreen: $images ")
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
//                Text(text = "Image Author - ${image.author}")
//                Text(text = "Image Data Taken - ${image.dataTaken}")
//                Text(text = "Image Data - ${image.data}")
//                Text(text = "Image Description - ${image.description}")
                    Text(text = "Image Display Name - ${image.displayName}")
                }
            }
        }
    )
}

@Composable
@Preview
fun Preview_ImagesScreen() {
    AndroRATTheme(darkTheme = false) {
        ImagesScreen(
            images = emptyList()
        )
    }
}

@Composable
@Preview
fun Preview_Dark_ImagesScreen() {
    AndroRATTheme(darkTheme = true) {
        ImagesScreen(
            images = emptyList()
        )
    }
}