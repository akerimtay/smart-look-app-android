package com.akerimtay.smartwardrobe.articles.ui

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.akerimtay.smartwardrobe.R
import com.akerimtay.smartwardrobe.articles.domain.GetArticlesLiveDataUseCase
import com.akerimtay.smartwardrobe.articles.domain.LoadArticlesUseCase
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseError
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import timber.log.Timber

class ArticlesViewModel(
    private val loadArticlesUseCase: LoadArticlesUseCase,
    private val getArticlesLiveDataUseCase: GetArticlesLiveDataUseCase,
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<ArticlesAction>()
    val actions: LiveData<ArticlesAction> = _actions

    private val _swipeRefreshLoading = MutableLiveData(false)
    val swipeRefreshLoading: LiveData<Boolean> = _swipeRefreshLoading

    val articles = liveData { emitSource(getArticlesLiveDataUseCase(Unit)) }

    init {
        loadArticles()
    }

    fun loadArticles() {
        launchSafe(
            start = { _swipeRefreshLoading.postValue(true) },
            finish = { _swipeRefreshLoading.postValue(false) },
            body = {
                loadArticlesUseCase(Unit)
            },
            handleError = {
                Timber.e(it, "Can't load articles")
                _actions.postValue(
                    when (it) {
                        is BaseError -> ArticlesAction.ShowMessage(messageResId = it.errorResId)
                        else -> ArticlesAction.ShowMessage(messageResId = R.string.error_load_articles)
                    }
                )
            }
        )
    }
}

sealed class ArticlesAction : Action {
    data class ShowMessage(@StringRes val messageResId: Int) : ArticlesAction()
}