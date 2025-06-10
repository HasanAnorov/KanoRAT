package com.ierusalem.androrat.features.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.ui.components.AndroRatAppBar
import com.ierusalem.androrat.core.ui.components.CommonAndroRatButton
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.ui.theme.dimens
import com.ierusalem.androrat.features.home.domain.HomeScreenClickIntents
import com.ierusalem.androrat.features.home.domain.HomeScreenState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    state: HomeScreenState,
    eventHandler: (HomeScreenClickIntents) -> Unit,
    onOpenMessageFragment: () -> Unit,
    onOpenImagesAndVideos: () -> Unit,
    onReadExternalStoragePermissionRequest: () -> Unit,
    onMultiplePermissionRequest: () -> Unit,
    onRecordAudioPermissionRequest: () -> Unit,
    onCameraPermissionRequest: () -> Unit,
    onStartEndlessService: () -> Unit,
    onStopEndlessService: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AndroRatAppBar(
                modifier = modifier,
                scrollBehavior = scrollBehavior,
                onNavIconPressed = { eventHandler(HomeScreenClickIntents.OpenDrawer) },
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navIcon = Icons.Default.Menu
            )
        },
        // Exclude ime and navigation bar padding so this can be added by the UserInput composable
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            content = {
                item {
                    Column(
                        modifier = Modifier.clip(RoundedCornerShape(12.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.onBackground.copy(0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(vertical = 12.dp)
                                    .fillMaxWidth(),
                                text = stringResource(R.string.endless_service),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onBackground,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        HorizontalDivider(
                            color = MaterialTheme.colorScheme.background,
                            thickness = 1.dp
                        )
                        Row(
                            modifier = Modifier.background(
                                MaterialTheme.colorScheme.onBackground.copy(0.03f)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .clickable { onStartEndlessService() }
                                    .weight(1F),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    text = stringResource(R.string.start),
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clickable { onStopEndlessService() }
                                    .weight(1F),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    modifier = Modifier.padding(vertical = 12.dp),
                                    text = stringResource(R.string.stop)
                                )
                            }
                        }
                    }
                }

                item {
                    CommonAndroRatButton(
                        onClick = onOpenImagesAndVideos,
                        text = stringResource(R.string.open_images_and_videos),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.dimens.spacing16)
                            .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                            .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f))
                    )
                }
                item {
                    CommonAndroRatButton(
                        modifier = Modifier
                            .semantics { testTagsAsResourceId = true }
                            .testTag(tag = "OpenMessageFragment")
                            .fillMaxWidth()
                            .padding(
                                start = MaterialTheme.dimens.spacing16,
                                end = MaterialTheme.dimens.spacing16,
                                bottom = MaterialTheme.dimens.spacing16
                            )
                            .clip(RoundedCornerShape(MaterialTheme.dimens.spacing12))
                            .background(color = MaterialTheme.colorScheme.onBackground.copy(0.1f)),
                        onClick = {
                            onOpenMessageFragment()
                        },
                        text = stringResource(R.string.open_messages)
                    )
                }
                item {
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
                }
                item {
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
                }
                item {
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
                }
                item {
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
                }
            }
        )
    }
}

@Preview
@Composable
fun HomeScreen_LightPreview() {
    AndroRATTheme(isDarkTheme = false) {
        HomeScreen(
            state = HomeScreenState(),
            onOpenMessageFragment = {},
            onOpenImagesAndVideos = {},
            onReadExternalStoragePermissionRequest = {},
            onMultiplePermissionRequest = {},
            onCameraPermissionRequest = {},
            onRecordAudioPermissionRequest = {},
            onStartEndlessService = {},
            onStopEndlessService = {},
            eventHandler = {}
        )
    }
}

@Preview
@Composable
fun HomeScreen_DarkPreview() {
    AndroRATTheme(isDarkTheme = true) {
        HomeScreen(
            state = HomeScreenState(),
            onOpenMessageFragment = {},
            onOpenImagesAndVideos = {},
            onReadExternalStoragePermissionRequest = {},
            onMultiplePermissionRequest = {},
            onCameraPermissionRequest = {},
            onRecordAudioPermissionRequest = {},
            onStartEndlessService = {},
            onStopEndlessService = {},
            eventHandler = {}
        )
    }
}