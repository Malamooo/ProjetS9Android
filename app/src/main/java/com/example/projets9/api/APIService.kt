package com.example.projets9.api

import com.example.projets9.BuildConfig
import com.example.projets9.led.LedStatus
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * APIService
 */
interface APIService {

    @GET("/status")
    suspend fun readStatus(@Query("identifier") identifier: String): LedStatus

    @POST("/status")
    suspend fun writeStatus(@Body status: LedStatus): LedStatus

    companion object {
        /**
         * Création d'un singleton pour la simplicité, mais normalement nous utilisons plutôt un
         * injecteur de dépendances.
         */
        val instance = build()

        /**
         * Création de l'objet qui nous permettra de faire les appels d'API
         */
        private fun build(): APIService {
            val gson = GsonBuilder().create() // JSON deserializer/serializer

            // Create the OkHttp Instance
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder().addHeader("Accept", "application/json").build()
                    chain.proceed(request)
                })
                .build()

            return Retrofit.Builder()
                .baseUrl(BuildConfig.URI_REMOTE_SERVER)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(APIService::class.java)
        }
    }
}