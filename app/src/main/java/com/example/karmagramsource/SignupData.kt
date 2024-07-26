package com.example.karmagramsource

data class SignupRequest (
    val name: String,
    val username : String,
    val password: String

)

data class SignupResponse(
    val success: Boolean,
    val message: String
)

