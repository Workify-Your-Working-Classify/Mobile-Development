package com.capbatu.workify.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("message") val message: String,
    @field:SerializedName("user") val user: UserResponse,
    @field:SerializedName("token") val token: String,
)
