package com.example.unsplashappsjetpack.network

import com.example.unsplashappsjetpack.Env
import com.example.unsplashappsjetpack.entity.NetworkResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface NetworkInstance {

    @GET("/photos")
    suspend fun photos(
        @Query("page") page: Int,
        @Query("client_id") clientId: String = Env.clientId
    ): List<NetworkResult.Photo>

    @GET("/photos/{id}")
    suspend fun photo(
        @Path("id") id: String,
        @Query("client_id") clientId: String = Env.clientId
    ): NetworkResult.Photo

    companion object {
        private val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(Env.baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun create(): NetworkInstance = retrofit.create(NetworkInstance::class.java)
    }
}