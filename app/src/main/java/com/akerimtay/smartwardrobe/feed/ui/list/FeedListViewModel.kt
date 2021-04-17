package com.akerimtay.smartwardrobe.feed.ui.list

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.paging.PagingData
import androidx.paging.map
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitItem
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitsUseCase
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.user.model.Gender
import timber.log.Timber

private const val PAGE_SIZE = 20

class FeedListViewModel(
    private val gender: Gender,
    private val getOutfitsUseCase: GetOutfitsUseCase,
) : BaseViewModel() {
    val outfits = liveData<PagingData<BaseContentItem<ItemContentType>>> {
        emitSource(
            getOutfitsUseCase(
                GetOutfitsUseCase.Param(
                    gender = when (gender) {
                        Gender.MALE -> OutfitGender.MALE
                        Gender.FEMALE -> OutfitGender.FEMALE
                    },
                    pageSize = PAGE_SIZE
                )
            ).map { pagingData ->
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