package com.capbatu.workify.ui.listArticle

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capbatu.workify.data.DataRepository
import com.capbatu.workify.data.remote.response.ArticleResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListArticleViewModel
    @Inject
    constructor(
        private val dataRepository: DataRepository,
    ) : ViewModel() {
        fun getArticle(): LiveData<Result<List<ArticleResponse>>> = dataRepository.getArticles().asLiveData()
    }
