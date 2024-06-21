package com.capbatu.workify.data

import com.capbatu.workify.data.remote.request.ActivityRequest
import com.capbatu.workify.data.remote.response.ActivityResponse
import com.capbatu.workify.data.remote.response.ArticleResponse
import com.capbatu.workify.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DataRepository
    @Inject
    constructor(
        private val apiService: ApiService,
    ) {
        suspend fun postActivity(
            uid: String,
            request: ActivityRequest,
        ): Flow<Result<Unit>> =
            flow {
                try {
                    apiService.postActivity(uid, request)
                    emit(Result.success(Unit))
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }

        fun getActivity(uid: String): Flow<Result<List<ActivityResponse>>> =
            flow {
                try {
                    val activities = apiService.getActivity(uid)
                    emit(Result.success(activities))
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }

        fun getArticles(): Flow<Result<List<ArticleResponse>>> =
            flow {
                try {
                    val articles = apiService.getArticle()
                    emit(Result.success(articles))
                } catch (e: Exception) {
                    emit(Result.failure(e))
                }
            }
    }
