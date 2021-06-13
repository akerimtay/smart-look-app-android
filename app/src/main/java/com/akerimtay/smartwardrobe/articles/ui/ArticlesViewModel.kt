package com.akerimtay.smartwardrobe.articles.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.articles.domain.GetArticlesLiveDataUseCase
import com.akerimtay.smartwardrobe.articles.domain.LoadArticlesUseCase
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.common.model.ErrorMessage
import com.akerimtay.smartwardrobe.common.utils.getErrorMessage
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.ArticleItem
import kotlinx.coroutines.flow.map
import timber.log.Timber

class ArticlesViewModel(
    private val loadArticlesUseCase: LoadArticlesUseCase,
    getArticlesLiveDataUseCase: GetArticlesLiveDataUseCase,
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<ArticlesAction>()
    val actions: LiveData<ArticlesAction> = _actions

    private val _swipeRefreshLoading = MutableLiveData(false)
    val swipeRefreshLoading: LiveData<Boolean> = _swipeRefreshLoading

    val articles: LiveData<List<BaseContentItem<ItemContentType>>> = getArticlesLiveDataUseCase(Unit).map { list ->
        list.map {
            ArticleItem(
                article = it,
                onItemClickListener = { _actions.postValue(ArticlesAction.ShowDetailScreen(it.id)) }
            )
        }
    }.asLiveData()

    init {
        loadArticles()
    }

    fun loadArticles() {
        launchSafe(
            start = { _swipeRefreshLoading.postValue(true) },
            finish = { _swipeRefreshLoading.postValue(false) },
            body = { loadArticlesUseCase(Unit) },
            handleError = {
                Timber.e(it, "Can't load articles")
                _actions.postValue(
                    ArticlesAction.ShowErrorMessage(it.getErrorMessage(defaultResId = R.string.error_load_articles))
                )
            }
        )
    }
}

sealed class ArticlesAction : Action {
    data class ShowErrorMessage(val errorMessage: ErrorMessage) : ArticlesAction()
    data class ShowDetailScreen(val articleId: Long) : ArticlesAction()
}