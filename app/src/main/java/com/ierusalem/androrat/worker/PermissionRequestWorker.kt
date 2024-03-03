package com.ierusalem.androrat.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ierusalem.androrat.screens.home.HomeScreenNavigation
import com.ierusalem.androrat.ui.navigation.DefaultNavigationEventDelegate
import com.ierusalem.androrat.ui.navigation.NavigationEventDelegate

class PermissionRequestWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters),
    NavigationEventDelegate<HomeScreenNavigation> by DefaultNavigationEventDelegate() {

    override suspend fun doWork(): Result {
        Log.d("ahi3646_request", "request work executed ")
        return Result.success()
    }
}
