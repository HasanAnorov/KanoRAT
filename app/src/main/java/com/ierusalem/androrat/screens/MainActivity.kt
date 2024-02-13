package com.ierusalem.androrat.screens

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import java.util.Date

class MainActivity : ComponentActivity() {

    val TAG = "ahi3646"

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
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                            readMediaPermissionsAndroid13.forEach { permission ->
                                homeViewModel.onPermissionResult(
                                    permission = permission,
                                    isGranted = perms[permission] == true
                                )
                            }
                        }else{
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

                                Manifest.permission.READ_EXTERNAL_STORAGE ->{
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
                    onReadExternalStoragePermissionRequest = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                            Log.d(TAG, "onCreate: ")
                            multipleReadMediaPermissionLauncher.launch(readMediaPermissionsAndroid13)
                        }else{
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
//                        val screenshot = takeScreenshot(this)
//                        val bitmap = captureScreenShot(window.decorView.rootView)
//                        homeViewModel.setPhoto(screenshot)
//                        takeScreenshot(this)
//                        Toast.makeText(this, "Took screenshot", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    private fun captureScreenShot(view: View): Bitmap {
        val returnedBitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(returnedBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) bgDrawable.draw(canvas)
        else canvas.drawColor(Color.WHITE)
        view.draw(canvas)
        return returnedBitmap
    }

    private fun takeScreenshot(activity: Activity): File {
        val now = Date()
        val date = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)

        val rootView = activity.window.decorView.rootView
        rootView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(rootView.drawingCache)
        rootView.isDrawingCacheEnabled = false

        val imageFile = File(
            activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "screenshot-$date.png"
        )

        val outputStream = FileOutputStream(imageFile)
        val quality = 100
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, outputStream)
        outputStream.flush()
        outputStream.close()

        return imageFile
    }
}
