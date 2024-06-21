package com.capbatu.workify.data.remote.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(
    @field:SerializedName("message") val message: String,
)
