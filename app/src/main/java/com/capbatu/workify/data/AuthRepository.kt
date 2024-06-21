package com.capbatu.workify.data

import com.capbatu.workify.data.local.AuthModel
import com.capbatu.workify.data.local.AuthPreferences
import com.capbatu.workify.data.remote.request.LoginRequest
import com.capbatu.workify.data.remote.request.RegisterRequest
import com.capbatu.workify.data.remote.response.LoginResponse
import com.capbatu.workify.data.remote.response.LogoutResponse
import com.capbatu.workify.data.remote.response.RegisterResponse
import com.capbatu.workify.data.remote.response.UserResponse
import com.capbatu.workify.data.remote.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepository
    @Inject
    constructor(
        private val apiService: ApiService,
        private val authPreferences: AuthPreferences,
    ) {
        suspend fun login(request: LoginRequest): Flow<Result<LoginResponse>> =
            flow {
                try {
                    emit(
                        Result.success(
                            apiService.login(request),
                        ),
                    )
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }.flowOn(Dispatchers.IO)

        suspend fun register(request: RegisterRequest): Flow<Result<RegisterResponse>> =
            flow {
                try {
                    emit(
                        Result.success(
                            apiService.register(request),
                        ),
                    )
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }.flowOn(Dispatchers.IO)

        suspend fun getUser(userId: String): Flow<Result<UserResponse>> =
            flow {
                try {
                    emit(
                        Result.success(
                            apiService.getUser(userId),
                        ),
                    )
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }.flowOn(Dispatchers.IO)

        suspend fun logout(): Flow<Result<LogoutResponse>> =
            flow {
                try {
                    emit(
                        Result.success(
                            apiService.logout(),
                        ),
                    )
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }.flowOn(Dispatchers.IO)

        fun getAuthSession(): Flow<AuthModel> = authPreferences.getAuthSession()

        suspend fun saveAuthSession(user: AuthModel) {
            authPreferences.saveAuthSession(user)
        }

        suspend fun removeAuthSession() {
            authPreferences.removeAuthSession()
        }
    }
