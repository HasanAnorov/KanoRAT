package com.ierusalem.androrat.screens

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.core.content.ContextCompat
import com.ierusalem.androrat.screens.home.HomeScreen
import com.ierusalem.androrat.screens.home.HomeViewModel
import com.ierusalem.androrat.ui.components.CameraPermissionTextProvider
import com.ierusalem.androrat.ui.components.MusicAndAudioPermissionTextProvider
import com.ierusalem.androrat.ui.components.PermissionDialog
import com.ierusalem.androrat.ui.components.PhoneCallPermissionTextProvider
import com.ierusalem.androrat.ui.components.PhotosAndVideosPermissionTextProvider
import com.ierusalem.androrat.ui.components.ReadExternalStoragePermissionTextProvider
import com.ierusalem.androrat.ui.components.RecordAudioPermissionTextProvider
import com.ierusalem.androrat.ui.theme.AndroRATTheme
import com.ierusalem.androrat.utility.openAppSettings
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : ComponentActivity() {

    private val recordAudioAndCallPhonePermissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val readMediaPermissionsAndroid13 = arrayOf(
        Manifest.permission.READ_MEDIA_AUDIO,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_VIDEO,
    )

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * To check the permissions whether granted or not
         */
//        ContextCompat.checkSelfPermission(
//            this, Manifest.permission.READ_MEDIA_IMAGES
//        ) == PackageManager.PERMISSION_GRANTED -> {
//            // You can use the API that requires the permission.
//        }

        /**
         * To check whether a device supports microphone and camera toggles,
         * add the logic that appears in the following code snippet:
         */

//        val sensorPrivacyManager = applicationContext
//            .getSystemService(SensorPrivacyManager::class.java)
//                as SensorPrivacyManager
//        val supportsMicrophoneToggle = sensorPrivacyManager
//            .supportsSensorToggle(Sensors.MICROPHONE)
//        val supportsCameraToggle = sensorPrivacyManager
//            .supportsSensorToggle(Sensors.CAMERA)

        /**
         * Check whether your app is running on a device that has a front-facing camera.
         */
//        if (applicationContext.packageManager.hasSystemFeature(
//                PackageManager.FEATURE_CAMERA_FRONT
//            )
//        ) {
//            // Continue with the part of your app's workflow that requires a
//            // front-facing camera.
//        } else {
//            // Gracefully degrade your app experience.
//        }


        setContent {
            val state by homeViewModel.state.collectAsState()
            AndroRATTheme {

                val dialogQueue = homeViewModel.visiblePermissionDialogQueue

                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        recordAudioAndCallPhonePermissions.forEach { permission ->
                            homeViewModel.onPermissionResult(
                                permission = permission,
                                isGranted = perms[permission] == true
                            )
                        }
                    }
                )

                /**
                For android 13
                These permissions and launchers needed for android 13

                //                val readImagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                //                    Manifest.permission.READ_MEDIA_IMAGES
                //                else
                //                    Manifest.permission.READ_EXTERNAL_STORAGE
                //                val readAudioPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                //                    Manifest.permission.READ_MEDIA_AUDIO
                //                else
                //                    Manifest.permission.READ_EXTERNAL_STORAGE
                //                val readVideoPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                //                    Manifest.permission.READ_MEDIA_VIDEO
                //                else
                //                    Manifest.permission.READ_EXTERNAL_STORAGE

                val readAudioPermissionForAndroid13Launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                homeViewModel.onPermissionResult(
                permission = readAudioPermission,
                isGranted = isGranted
                )
                }
                )
                val readImagesPermissionForAndroid13Launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                homeViewModel.onPermissionResult(
                permission = readImagePermission,
                isGranted = isGranted
                )
                }
                )
                val readVideoPermissionForAndroid13Launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted ->
                homeViewModel.onPermissionResult(
                permission = readVideoPermission,
                isGranted = isGranted
                )
                }
                )
                 */

                //this launcher intended to use only for devices running on android 13 and above
                val multipleReadMediaPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            readMediaPermissionsAndroid13.forEach { permission ->
                                homeViewModel.onPermissionResult(
                                    permission = permission,
                                    isGranted = perms[permission] == true
                                )
                            }
                        } else {
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        }
                    }
                )

                val readExternalStoragePermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        homeViewModel.onPermissionResult(
                            permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                                Manifest.permission.READ_MEDIA_IMAGES
                            else {
                                if (ContextCompat.checkSelfPermission(
                                        this, Manifest.permission.READ_MEDIA_IMAGES
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                } else {
                                    Manifest.permission.RECORD_AUDIO
                                }
                            },
                            isGranted = isGranted
                        )
                    }
                )

                val recordAudioPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        homeViewModel.onPermissionResult(
                            permission = Manifest.permission.RECORD_AUDIO,
                            isGranted = isGranted
                        )
                    }
                )

                val cameraResultPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        homeViewModel.onPermissionResult(
                            permission = Manifest.permission.CAMERA,
                            isGranted = isGranted
                        )
                    }
                )

                dialogQueue
                    .reversed()
                    .forEach { permission ->
                        PermissionDialog(
                            permissionTextProvider = when (permission) {

                                Manifest.permission.READ_MEDIA_AUDIO -> {
                                    MusicAndAudioPermissionTextProvider()
                                }

                                /**
                                 * No need to add Manifest.permission.READ_MEDIA_VIDEO
                                 * this will make rational dialog pop up two times for same warning
                                 * Once user allow for READ_VIDEOS or READ_IMAGE , both permissions
                                 * will be accessible for app
                                 */
                                Manifest.permission.READ_MEDIA_IMAGES -> {
                                    PhotosAndVideosPermissionTextProvider()
                                }

                                Manifest.permission.READ_EXTERNAL_STORAGE -> {
                                    ReadExternalStoragePermissionTextProvider()
                                }

                                Manifest.permission.CAMERA -> {
                                    CameraPermissionTextProvider()
                                }

                                Manifest.permission.RECORD_AUDIO -> {
                                    RecordAudioPermissionTextProvider()
                                }

                                Manifest.permission.CALL_PHONE -> {
                                    PhoneCallPermissionTextProvider()
                                }

                                else -> return@forEach
                            },
                            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(permission),
                            onDismiss = homeViewModel::dismissDialog,
                            onOkClick = {
                                homeViewModel.dismissDialog()
                                multiplePermissionResultLauncher.launch(
                                    arrayOf(permission)
                                )
                            },
                            onGoToAppSettingsClick = ::openAppSettings
                        )
                    }

                HomeScreen(
                    state = state,
                    onSaveScreenshotClick = {
                        if (state.screenshot != null) {
                            saveMediaToStorage(state.screenshot!!)
                        } else {
                            Toast.makeText(this, "Can not save!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    onReadExternalStoragePermissionRequest = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            multipleReadMediaPermissionLauncher.launch(readMediaPermissionsAndroid13)
                        } else {
                            readExternalStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    },
                    onMultiplePermissionRequest = {
                        multiplePermissionResultLauncher.launch(recordAudioAndCallPhonePermissions)
                    },
                    onRecordAudioPermissionRequest = {
                        recordAudioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    },
                    onCameraPermissionRequest = {
                        cameraResultPermissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    onTakeScreenShotClick = {
                        homeViewModel.updatePhoto(it)
                    }
                )
            }
        }
    }

    private fun saveMediaToStorage(bitmap: ImageBitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }
        fos?.use {
            bitmap.asAndroidBitmap().compress( Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Saved to Photos", Toast.LENGTH_SHORT).show()
        }
    }

}
