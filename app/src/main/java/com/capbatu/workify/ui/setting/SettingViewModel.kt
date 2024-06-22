package com.capbatu.workify.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capbatu.workify.data.AuthRepository
import com.capbatu.workify.data.remote.response.LogoutResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        suspend fun logout(): Flow<Result<LogoutResponse>> = authRepository.logout()

        fun removeSession() {
            viewModelScope.launch {
                authRepository.removeAuthSession()
            }
        }
    }
