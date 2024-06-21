package com.capbatu.workify.data.remote.request

data class ActivityRequest(
    val jenisKegiatan: String = "not set",
    val namaKegiatan: String,
    val pelaksana: String,
    val tanggal: String,
    val jam: String,
    val lama: String,
    val Deskripsi: String,
    val kepentinganKegiatan: Int,
    val kepentinganPelaksana: Int,
)
