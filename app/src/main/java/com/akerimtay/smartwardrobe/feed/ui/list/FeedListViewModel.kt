package com.akerimtay.smartwardrobe.feed.ui.list

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitItem
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitsUseCaseAsFlow
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.user.model.Gender
import timber.log.Timber

private const val PAGE_SIZE = 20

class FeedListViewModel(
    private val gender: Gender,
    private val getOutfitsUseCaseAsFlow: GetOutfitsUseCaseAsFlow,
) : BaseViewModel() {
    val outfits = liveData<PagingData<BaseContentItem<ItemContentType>>> {
        emitSource(
            getOutfitsUseCaseAsFlow(
                GetOutfitsUseCaseAsFlow.Param(
                    gender = when (gender) {
                        Gender.MALE -> OutfitGender.MALE
                        Gender.FEMALE -> OutfitGender.FEMALE
                    },
                    pageSize = PAGE_SIZE
                )
            ).cachedIn(viewModelScope)
                .asLiveData()
                .map { pagingData ->
                    pagingData.map {
                        OutfitItem(
                            outfit = it,
                            onItemClickListener = {
                                Timber.e("click: $it")
                            }
                        )
                    }
                }
        )
    }
}