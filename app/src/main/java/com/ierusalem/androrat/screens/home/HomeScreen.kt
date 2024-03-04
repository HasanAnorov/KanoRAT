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
import androidx.compose.ui.res.stringResource
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
    onOpenImagesAndVideos: () -> Unit,
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
                    contentDescription = stringResource(id = R.string.screenshot_place_debug),
                )
            } else {
                Image(
                    modifier = Modifier
                        .height(390.dp)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    bitmap = state.screenshot,
                    contentDescription = stringResource(R.string.screenshot_place_debug),
                )
            }
            CommonAndroRatButton(
                onClick = onSaveScreenshotClick,
                text = stringResource(R.string.save_screenshot_to_gallery_debug),
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
                onClick = onOpenImagesAndVideos,
                text = "Open Images and Videos Fragment",
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
                    onOpenMessageFragment()
                },
                text = stringResource(R.string.open_messagefragment_debug),
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
                text = stringResource(R.string.call_phone_and_record_audio_permission_debug),
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
                text = stringResource(R.string.read_media_files_debug),
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
                text = stringResource(R.string.record_audio_permission_debug),
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
                text = stringResource(R.string.camera_permission_debug),
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
                    } catch (error: Throwable) {
                        Toast.makeText(
                            context,
                            context.resources.getString(R.string.can_not_take_screenshot_debug),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                text = stringResource(R.string.take_a_screenshot_debug),
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
            onOpenImagesAndVideos = {},
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
            onOpenImagesAndVideos = {},
            onReadExternalStoragePermissionRequest = {},
            onMultiplePermissionRequest = {},
            onCameraPermissionRequest = {},
            onRecordAudioPermissionRequest = {},
            onTakeScreenShotClick = {}
        )
    }
}