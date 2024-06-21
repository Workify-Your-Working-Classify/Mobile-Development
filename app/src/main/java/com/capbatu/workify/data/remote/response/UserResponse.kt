package com.capbatu.workify.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("uid") val userId: String,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("displayName") val displayName: String,
)
