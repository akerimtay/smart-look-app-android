package com.akerimtay.smartwardrobe.feed.ui.list

import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitsUseCaseAsFlow
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.user.model.Gender

private const val PAGE_SIZE = 20

class FeedListViewModel(
    private val gender: Gender,
    private val getOutfitsUseCaseAsFlow: GetOutfitsUseCaseAsFlow
) : BaseViewModel() {
    val outfits = liveData {
        emitSource(
            getOutfitsUseCaseAsFlow(
                GetOutfitsUseCaseAsFlow.Param(
                    gender = when (gender) {
                        Gender.MALE -> OutfitGender.MALE
                        Gender.FEMALE -> OutfitGender.FEMALE
                    },
                    pageSize = PAGE_SIZE
                )
            ).asLiveData()
        )
    };
}