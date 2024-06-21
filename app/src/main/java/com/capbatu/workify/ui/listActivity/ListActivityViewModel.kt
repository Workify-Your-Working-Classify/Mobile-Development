package com.capbatu.workify.ui.listActivity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capbatu.workify.data.AuthRepository
import com.capbatu.workify.data.DataRepository
import com.capbatu.workify.data.local.AuthModel
import com.capbatu.workify.data.remote.response.ActivityResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListActivityViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val dataRepository: DataRepository,
    ) : ViewModel() {
        fun getAuthSession(): LiveData<AuthModel> = authRepository.getAuthSession().asLiveData()

        fun getActivity(uid: String): LiveData<Result<List<ActivityResponse>>> = dataRepository.getActivity(uid).asLiveData()
    }
