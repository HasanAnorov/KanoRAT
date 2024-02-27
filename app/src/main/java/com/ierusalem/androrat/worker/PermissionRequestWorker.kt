package com.ierusalem.androrat.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class PermissionRequestWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        Log.d("ahi3646_request", "doWork: executed ")
        return Result.success()
    }
}
