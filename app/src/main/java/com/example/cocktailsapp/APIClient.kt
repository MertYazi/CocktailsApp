package com.example.cocktailsapp

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        fun getService(): CocktailsService {
            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor { chain ->
                    val url = chain
                        .request()
                        .url
                        .newBuilder()
                        .build()
                    chain.proceed(chain.request().newBuilder().url(url).build())
                }
                .build()

            val build = Retrofit.Builder()
                .client(client)
                .baseUrl("https://thecocktaildb.com/api/json/v1/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return build.create(CocktailsService::class.java)
        }
    }
}