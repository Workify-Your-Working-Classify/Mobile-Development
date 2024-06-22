package com.capbatu.workify.data.remote.request

data class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String,
)
