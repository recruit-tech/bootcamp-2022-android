package com.example.mygithubapp.repository.retrofit

import androidx.lifecycle.LiveData
import com.example.mygithubapp.data.UserListResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubAccessService {
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/users")
    fun getUserList(@Query("q") q: String): Call<UserListResponse>

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubAccessService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubAccessService::class.java)
        }
    }
}