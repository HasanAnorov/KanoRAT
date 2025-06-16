package com.ierusalem.androrat.features.images.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.components.AndroRatAppBar
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.ui.theme.MontserratFontFamily
import com.ierusalem.androrat.features.home.domain.model.Image

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagesScreen(
    modifier: Modifier = Modifier,
    onUploadClick: () -> Unit,
    images: List<Image>,
    eventHandler: (ImagesScreenEvents) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AndroRatAppBar(
                modifier = modifier,
                onNavIconPressed = { eventHandler(ImagesScreenEvents.NavIconClick) },
                title = {
                    Text(
                        text = stringResource(R.string.images),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navIcon = Icons.AutoMirrored.Filled.ArrowBack
            )
        },
        // Exclude ime and navigation bar padding so this can be added by the UserInput composable
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime)
    ) { paddingValues ->
        Log.d("ahi3646_hh", "ImagesScreen: $images ")
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {},
                        enabled = false
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.image_03),
                            contentDescription = "images",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp),
                        text = stringResource(R.string.images),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        modifier = Modifier
                            .weight(1F)
                            .padding(horizontal = 4.dp),
                        text = "(23)",
                        fontFamily = MontserratFontFamily,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Button(
                        modifier = Modifier.padding(end = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {},
                        content = {
                            Text("Upload")
                        }
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { },
                        enabled = false
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.video_file_light),
                            contentDescription = "Videos",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        text = stringResource(R.string.videos),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(
                        modifier = Modifier
                            .weight(1F)
                            .padding(horizontal = 4.dp),
                        text = "(425)",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        fontFamily = MontserratFontFamily,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

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
}

@Composable
@Preview
fun Preview_ImagesScreen() {
    AndroRATTheme(isDarkTheme = false) {
        ImagesScreen(
            onUploadClick = {},
            images = emptyList(),
            eventHandler = {}
        )
    }
}

@Composable
@Preview
fun Preview_Dark_ImagesScreen() {
    AndroRATTheme(isDarkTheme = true) {
        ImagesScreen(
            onUploadClick = {},
            images = emptyList(),
            eventHandler = {}
        )
    }
}