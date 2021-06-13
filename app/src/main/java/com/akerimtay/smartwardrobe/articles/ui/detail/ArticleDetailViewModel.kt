package com.akerimtay.smartwardrobe.articles.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.akerimtay.smartwardrobe.articles.domain.GetArticleByIdAsFlowUseCase
import com.akerimtay.smartwardrobe.articles.domain.GetArticleByIdAsFlowUseCase.Param
import com.akerimtay.smartwardrobe.articles.model.Article
import com.akerimtay.smartwardrobe.common.base.BaseViewModel

class ArticleDetailViewModel(
    articleId: Long,
    getArticleByIdAsFlowUseCase: GetArticleByIdAsFlowUseCase
) : BaseViewModel() {
    val article: LiveData<Article?> = getArticleByIdAsFlowUseCase(Param(articleId = articleId)).asLiveData()
}