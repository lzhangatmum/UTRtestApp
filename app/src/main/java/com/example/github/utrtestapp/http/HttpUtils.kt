package com.example.github.utrtestapp.http

import com.example.github.utrtestapp.bean.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface HttpUtils{
    @GET("/users/ad")
    fun signUp(): Observable<UserBean>
    @GET("users/ad/followers")
    fun loadFollower(): Observable<ArrayList<InfoBean>>
    @GET("users/ad/received_events")
    fun loadRecevied(): Observable<ArrayList<InfoBean>>
    @GET("users/ad/repos")
    fun loadRepos(): Observable<ArrayList<InfoBean>>
    @GET("users/ad/subscriptions")
    fun loadSubscriptions(): Observable<ArrayList<InfoBean>>

    companion object {
        fun create(): HttpUtils {
            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            val retrofit = Retrofit.Builder()
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .build()

            return retrofit.create(HttpUtils::class.java)
        }
    }
}