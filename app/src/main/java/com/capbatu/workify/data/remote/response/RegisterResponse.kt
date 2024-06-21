package com.capbatu.workify.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @field:SerializedName("uid") val userId: String,
)
