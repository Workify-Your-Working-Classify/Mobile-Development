package com.capbatu.workify.ui.register

import androidx.lifecycle.ViewModel
import com.capbatu.workify.data.AuthRepository
import com.capbatu.workify.data.remote.request.RegisterRequest
import com.capbatu.workify.data.remote.response.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        suspend fun register(
            displayName: String,
            email: String,
            password: String,
        ): Flow<Result<RegisterResponse>> = authRepository.register(RegisterRequest(displayName, email, password))
    }
