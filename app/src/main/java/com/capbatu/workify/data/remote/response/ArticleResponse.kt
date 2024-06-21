package com.capbatu.workify.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArticleResponse(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("author") val author: String,
    @field:SerializedName("source") val source: String,
    @field:SerializedName("picture1") val picture1: String,
    @field:SerializedName("picture2") val picture2: String,
    @field:SerializedName("title") val title: String,
) : Parcelable
