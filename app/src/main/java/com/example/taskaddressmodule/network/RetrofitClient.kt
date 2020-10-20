package com.example.taskaddressmodule.network

import com.example.taskaddressmodule.utils.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    val retrofitClientInstance: ApiService by lazy {

        val httpClientBuilder = OkHttpClient.Builder()

        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(15, TimeUnit.SECONDS)

        // add logs interceptor
        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        // add logging as last interceptor
        httpClientBuilder.addInterceptor(logging)

        val okHttpClient = httpClientBuilder.build()
        // retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }
}