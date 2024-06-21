package com.capbatu.workify.ui.addActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capbatu.workify.data.AuthRepository
import com.capbatu.workify.data.DataRepository
import com.capbatu.workify.data.local.AuthModel
import com.capbatu.workify.data.remote.request.ActivityRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddActivityViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val dataRepository: DataRepository,
    ) : ViewModel() {
        fun getAuthSession(): LiveData<AuthModel> = authRepository.getAuthSession().asLiveData()

        suspend fun postActivity(
            uid: String,
            activity: ActivityRequest,
        ): Flow<Result<Unit>> = dataRepository.postActivity(uid, activity)
    }
