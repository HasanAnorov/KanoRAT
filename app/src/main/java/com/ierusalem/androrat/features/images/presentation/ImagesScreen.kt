package com.ierusalem.androrat.features.images.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.components.AndroRatAppBar
import com.ierusalem.androrat.core.ui.components.EmptyScreen
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
                            contentDescription = stringResource(R.string.images),
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
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                append("( ")
                            }
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.outline)) { // change number color
                                append(images.size.toString())
                            }
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                append(" )")
                            }
                        },
                        fontFamily = MontserratFontFamily,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Button(
                        modifier = Modifier.padding(end = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = onUploadClick,
                        content = {
                            Text(
                                text = stringResource(R.string.upload),
                                style = MaterialTheme.typography.titleSmall
                            )
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
                        onClick = {},
                        enabled = false
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.video),
                            contentDescription = stringResource(R.string.videos),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(start = 4.dp),
                        text = stringResource(R.string.videos),
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        modifier = Modifier
                            .weight(1F)
                            .padding(horizontal = 4.dp),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                append("( ")
                            }
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.outline)) { // change number color
                                append("887")
                            }
                            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                                append(" )")
                            }
                        },
                        fontFamily = MontserratFontFamily,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Button(
                        modifier = Modifier.padding(end = 16.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {},
                        content = {
                            Text(
                                text = stringResource(R.string.upload),
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    )
                }
            }
            if (images.isEmpty()) {
                EmptyScreen(modifier = Modifier.fillMaxSize())
            } else {
                var selectedImage by remember { mutableStateOf<Image?>(null) }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(images) { image ->
                        Column(
                            modifier = Modifier
                                .aspectRatio(1f) // Ensures square cells
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedImage = image }, // Click to open dialog,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AsyncImage(
                                model = image.uri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                if (selectedImage != null) {
                    ImagePreviewDialog(
                        image = selectedImage!!,
                        onDismiss = { selectedImage = null }
                    )
                }
            }
        }
    }
}

@Composable
fun ImagePreviewDialog(
    image: Image,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Black, shape = RoundedCornerShape(12.dp))
                .clickable { onDismiss() }
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display image
                AsyncImage(
                    model = image.uri,
                    contentDescription = image.displayName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Display metadata
                MetadataRow("Name ", image.displayName)
                MetadataRow("MIME Type ", image.mimeType)
                MetadataRow("Size ", image.size)
                MetadataRow("Date Taken ", image.dateTaken)
                MetadataRow("Folder ", image.folderName)
                MetadataRow("Path ", image.relativePath)
                MetadataRow("Width ", image.width?.toString()?.plus(" px"))
                MetadataRow("Height ", image.height?.toString()?.plus(" px"))
                MetadataRow("Author ", image.author)
                MetadataRow("Description ", image.description)
                MetadataRow("Orientation ", image.orientation?.toString())
                MetadataRow("Latitude ", image.latitude?.toString())
                MetadataRow("Longitude ", image.longitude?.toString())
                MetadataRow("Camera ", image.cameraModel)
                MetadataRow("ISO ", image.iso)
                MetadataRow("Aperture ", image.aperture)
                MetadataRow("Exposure ", image.exposureTime)

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.tap_anywhere_to_close),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun MetadataRow(label: String, value: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = Color.LightGray,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value ?: "null", // show "null" if value is null
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
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