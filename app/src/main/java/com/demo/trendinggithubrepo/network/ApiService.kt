package com.demo.trendinggithubrepo.network

import com.demo.trendinggithubrepo.data.api_models.GitHubRepo
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("repositories")
    suspend fun getRepositoriesList(
        @Query("language") language: String,
        @Query("since") since: String,
        @Query("spoken_language_code") spokenLanguage: String
    ) : Response<List<GitHubRepo>>

    companion object {

        operator fun invoke(networkConnectionInterceptor: NetworkConnectionInterceptor): ApiService {

            val baseUrl = "https://ghapi.huchen.dev/"

            val okHttpClient = OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}