package com.capbatu.workify.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capbatu.workify.data.AuthRepository
import com.capbatu.workify.data.local.AuthModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) : ViewModel() {
        private val _currentPage = MutableLiveData<Int>()
        val currentPage: LiveData<Int> = _currentPage

        fun setCurrentPage(page: Int) {
            _currentPage.value = page
        }

        init {
            _currentPage.value = 0
        }

        fun getAuthSession(): LiveData<AuthModel> = authRepository.getAuthSession().asLiveData()
    }
