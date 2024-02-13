package com.ierusalem.androrat.screens.home

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.ui.components.CommonAndroRatButton
import com.ierusalem.androrat.ui.theme.AndroRATTheme
import com.ierusalem.androrat.ui.theme.dimens

@Composable
fun HomeScreen(
    state: HomeScreenState,
    onReadExternalStoragePermissionRequest: () -> Unit,
    onMultiplePermissionRequest: () -> Unit,
    onRecordAudioPermissionRequest: () -> Unit,
    onCameraPermissionRequest: () -> Unit,
    onTakeScreenShotClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
//            val bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.ic_launcher_foreground)
            if (state.screenshot == null) {
                Image(
                    modifier = Modifier
                        .height(390.dp)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = "Profile icon",
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondaryContainer)
                )
            } else {
                val bitmap = BitmapFactory.decodeFile(state.screenshot.name)
                Image(
                    modifier = Modifier
                        .height(390.dp)
                        .padding(horizontal = 16.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Profile icon",
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondaryContainer)
                )
            }

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
                onClick = onTakeScreenShotClick,
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
            onReadExternalStoragePermissionRequest = {},
            onMultiplePermissionRequest = {},
            onCameraPermissionRequest = {},
            onRecordAudioPermissionRequest = {},
            onTakeScreenShotClick = {}
        )
    }
}