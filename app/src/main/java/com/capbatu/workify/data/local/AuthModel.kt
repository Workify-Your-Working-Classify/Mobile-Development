package com.capbatu.workify.data.local

data class AuthModel(
    val userId: String,
    val displayName: String,
    val email: String,
    val token: String,
    val isLogin: Boolean = false,
    val themePreferences: Boolean = false,
)
