package com.networkapi

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by zeeshan on 12/21/2016.
 */

object NetworkApi {

    @JvmStatic
    fun getClient(): Retrofit {
        return Retrofit.Builder().baseUrl(BuildConfig.WebServiceUrl)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create()).client(getOkHttpClient()).build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val headerInterceptor = Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder()
                .method(request.method(), request.body()).build()

            val headers = chain.proceed(request)
            val localization = headers.headers()[Keys.X_LOCALIZATION]
            val platform = headers.headers()[Keys.X_PLATFORM]
            val os = headers.headers()[Keys.X_OSVERSION]
            val device = headers.headers()[Keys.X_DEVICE]
            val appVersion = headers.headers()[Keys.X_APP_VERSION]
            val authorization = headers.headers()[Keys.X_AUTHORIZATION]

            /*println("${Keys.X_LOCALIZATION}::$localization")
            println("${Keys.X_PLATFORM}::$platform")
            println("${Keys.X_OSVERSION}::$os")
            println("${Keys.X_DEVICE}::$device")
            println("${Keys.X_APP_VERSION}::$appVersion")
            println("${Keys.X_AUTHORIZATION}::$authorization")*/

            headers
        }

        val builder = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        builder.addInterceptor(interceptor)
        builder.addInterceptor(headerInterceptor)
        builder.readTimeout(2, TimeUnit.MINUTES)
        builder.connectTimeout(2, TimeUnit.MINUTES)
        return builder.build()
    }

}