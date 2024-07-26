package com.example.karmagramsource

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Define the User data class
data class User(
    val name: String,
    val pwd: String
)

interface ApiService {
    @GET("/login/{username}")
    suspend fun getUser(@Path("username") username: String): User
    @POST("/signup")
    fun signup(@Body request: SignupRequest): Call<SignupResponse>
}

object ApiClient {
    private const val BASE_URL = "http://192.168.1.100:8000/"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}