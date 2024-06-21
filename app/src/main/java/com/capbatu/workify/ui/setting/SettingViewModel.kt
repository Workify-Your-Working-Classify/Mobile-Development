package com.capbatu.workify.ui.setting

import androidx.lifecycle.ViewModel
import com.capbatu.workify.data.AuthRepository
import com.capbatu.workify.data.remote.response.LogoutResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        suspend fun logout(): Flow<Result<LogoutResponse>> {
            authRepository.removeAuthSession()
            return authRepository.logout()
        }
    }
