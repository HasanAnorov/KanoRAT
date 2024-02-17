package com.ierusalem.androrat.screens.home

import android.graphics.Picture
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.ui.components.CommonAndroRatButton
import com.ierusalem.androrat.ui.theme.AndroRATTheme
import com.ierusalem.androrat.ui.theme.dimens
import dev.shreyaspatil.capturable.capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalComposeApi::class)
@Composable
fun HomeScreen(
    state: HomeScreenState,
    onOpenMessageFragment: () -> Unit,
    onSaveScreenshotClick: () -> Unit,
    onReadExternalStoragePermissionRequest: () -> Unit,
    onMultiplePermissionRequest: () -> Unit,
    onRecordAudioPermissionRequest: () -> Unit,
    onCameraPermissionRequest: () -> Unit,
    onTakeScreenShotClick: (bitmap: ImageBitmap) -> Unit,
) {
    val captureController = rememberCaptureController()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val picture = remember { Picture() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .capturable(captureController)
            .background(MaterialTheme.colorScheme.background)
            .drawWithCache {
                // Example that shows how to redirect rendering to an Android Picture and then
                // draw the picture into the original destination
                val width = this.size.width.toInt()
                val height = this.size.height.toInt()

                onDrawWithContent {
                    val pictureCanvas =
                        androidx.compose.ui.graphics.Canvas(
                            picture.beginRecording(
                                width,
                                height
                            )
                        )
                    // requires at least 1.6.0-alpha01+
                    draw(this, this.layoutDirection, pictureCanvas, this.size) {
                        this@onDrawWithContent.drawContent()
                    }
                    picture.endRecording()

                    drawIntoCanvas { canvas -> canvas.nativeCanvas.drawPicture(picture) }
                }
            },
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            if (state.screenshot == null) {
                Image(
                    modifier = Modifier
                        .height(390.dp)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Screenshot place",
                )
            } else {
                Image(
                    modifier = Modifier
                        .height(390.dp)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    bitmap = state.screenshot,
                    contentDescription = "Screenshot place",
                )
            }
            CommonAndroRatButton(
                onClick = onSaveScreenshotClick,
                text = "Save screenshot to gallery",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.spacing16,
                        end = MaterialTheme.dimens.spacing16,
                        bottom = MaterialTheme.dimens.spacing16
                    )
                    .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                    .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
            )

            CommonAndroRatButton(
                onClick = onOpenMessageFragment,
                text = "Open MessageFragment",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.spacing16,
                        end = MaterialTheme.dimens.spacing16,
                        bottom = MaterialTheme.dimens.spacing16
                    )
                    .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                    .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
            )

            CommonAndroRatButton(
                onClick = onMultiplePermissionRequest,
                text = "Call Phone and Record Audio Permission",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.spacing16,
                        end = MaterialTheme.dimens.spacing16,
                        bottom = MaterialTheme.dimens.spacing16
                    )
                    .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                    .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
            )

            CommonAndroRatButton(
                onClick = onReadExternalStoragePermissionRequest,
                text = "Read Media Files",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.spacing16,
                        end = MaterialTheme.dimens.spacing16,
                        bottom = MaterialTheme.dimens.spacing16
                    )
                    .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                    .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
            )

            CommonAndroRatButton(
                onClick = onRecordAudioPermissionRequest,
                text = "Record Audio Permission",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.spacing16,
                        end = MaterialTheme.dimens.spacing16,
                        bottom = MaterialTheme.dimens.spacing16
                    )
                    .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                    .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
            )

            CommonAndroRatButton(
                onClick = onCameraPermissionRequest,
                text = "Camera Permission",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = MaterialTheme.dimens.spacing16,
                        end = MaterialTheme.dimens.spacing16,
                        bottom = MaterialTheme.dimens.spacing16
                    )
                    .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                    .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
            )

            CommonAndroRatButton(
                onClick = {
                    val bitmapAsync = captureController.captureAsync()
                    try {
                        coroutineScope.launch {
                            val bitmap = bitmapAsync.await()
                            onTakeScreenShotClick(bitmap)
                        }
                    }catch (error: Throwable){
                        Toast.makeText(context, "Can't take a screenshot", Toast.LENGTH_SHORT).show()
                    }
                },
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
        HomeScreen(
            state = HomeScreenState(),
            onOpenMessageFragment = {},
            onSaveScreenshotClick = {},
            onReadExternalStoragePermissionRequest = {},
            onMultiplePermissionRequest = {},
            onCameraPermissionRequest = {},
            onRecordAudioPermissionRequest = {},
            onTakeScreenShotClick = {}
        )
    }
}

@Preview
@Composable
fun HomeScreen_DarkPreview() {
    AndroRATTheme(darkTheme = true) {
        HomeScreen(
            state = HomeScreenState(),
            onOpenMessageFragment = {},
            onSaveScreenshotClick = {},
            onReadExternalStoragePermissionRequest = {},
            onMultiplePermissionRequest = {},
            onCameraPermissionRequest = {},
            onRecordAudioPermissionRequest = {},
            onTakeScreenShotClick = {}
        )
    }
}