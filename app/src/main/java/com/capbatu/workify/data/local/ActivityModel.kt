package com.capbatu.workify.data.local

data class ActivityModel(
    val jenisKegiatan: String = "not set",
    val namaKegiatan: String,
    val kepentinganKegiatan: Int,
    val kepentinganPelaksana: Int,
    val pelaksana: String,
    val tanggal: String,
    val jam: String,
    val lama: String,
    val deskripsi: String,
)
