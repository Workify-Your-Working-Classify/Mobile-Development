package com.capbatu.workify.data.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActivityResponse(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("pelaksana") val pelaksana: String,
    @field:SerializedName("jenisKegiatan") val jenisKegiatan: String,
    @field:SerializedName("Deskripsi") val deskripsi: String,
    @field:SerializedName("statusKegiatan") val statusKegiatan: Float,
    @field:SerializedName("hasilPrediksi") val hasilPrediksi: Float,
    @field:SerializedName("totalPoint") val totalPoint: Float,
    @field:SerializedName("kepentinganKegiatan") val kepentinganKegiatan: Int,
    @field:SerializedName("kepentinganPelaksana") val kepentinganPelaksana: Int,
    @field:SerializedName("namaKegiatan") val namaKegiatan: String,
    @field:SerializedName("jam") val jam: String,
    @field:SerializedName("tanggal") val tanggal: String,
    @field:SerializedName("lama") val lama: String,
) : Parcelable
