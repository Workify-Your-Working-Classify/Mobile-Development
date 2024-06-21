package com.capbatu.workify.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capbatu.workify.data.AuthRepository
import com.capbatu.workify.data.local.AuthModel
import com.capbatu.workify.data.remote.request.LoginRequest
import com.capbatu.workify.data.remote.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        suspend fun login(
            email: String,
            password: String,
        ): Flow<Result<LoginResponse>> = authRepository.login(LoginRequest(email, password))

        fun saveAuthSession(
            userId: String,
            displayName: String,
            email: String,
            token: String,
        ) {
            viewModelScope.launch {
                authRepository.saveAuthSession(AuthModel(userId, displayName, email, token))
            }
        }
    }
