package com.capbatu.workify.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capbatu.workify.data.AuthRepository
import com.capbatu.workify.data.DataRepository
import com.capbatu.workify.data.local.AuthModel
import com.capbatu.workify.data.remote.response.ActivityResponse
import com.capbatu.workify.data.remote.response.ArticleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
    @Inject
    constructor(
        private val authRepository: AuthRepository,
        private val dataRepository: DataRepository,
    ) : ViewModel() {
        private val _stressPoint = MutableLiveData<Int>()
        val stressPoint: LiveData<Int> get() = _stressPoint

        private val _stressLevel = MutableLiveData<String>()
        val stressLevel: LiveData<String> get() = _stressLevel

        init {
            _stressPoint.value = 0
            _stressLevel.value = "Zen Zone"
        }

        fun getAuthSession(): LiveData<AuthModel> = authRepository.getAuthSession().asLiveData()

        fun getActivity(userId: String): LiveData<Result<List<ActivityResponse>>> {
            val activityResult = dataRepository.getActivity(userId).asLiveData()
            activityResult.observeForever { result ->
                result.getOrNull()?.let { activities ->
                    val totalPoints = sumPoint(activities)
                    _stressPoint.value = totalPoints
                    _stressLevel.value = getStressLevel(totalPoints)
                }
            }
            return activityResult
        }

        fun getArticle(): LiveData<Result<List<ArticleResponse>>> = dataRepository.getArticles().asLiveData()

        fun sumPoint(activities: List<ActivityResponse>): Int = activities.map { it.totalPoint }.sum().toInt()

        fun getStressLevel(stressPoint: Int): String {
            return when (stressPoint) {
                in 0..24 -> "Zen Zone"
                in 25..49 -> "Chill Mode"
                in 50..74 -> "Busy Bee"
                else -> "Pressure Cooker"
            }
        }
    }
