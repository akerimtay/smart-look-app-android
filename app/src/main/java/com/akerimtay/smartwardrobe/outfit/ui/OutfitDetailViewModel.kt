package com.akerimtay.smartwardrobe.outfit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitByIdAsFlowUseCase
import com.akerimtay.smartwardrobe.outfit.model.Outfit

class OutfitDetailViewModel(
    private val outfitId: Long,
    private val getOutfitByIdAsFlowUseCase: GetOutfitByIdAsFlowUseCase
) : BaseViewModel() {
    val outfit: LiveData<Outfit?> = liveData {
        emitSource(getOutfitByIdAsFlowUseCase(GetOutfitByIdAsFlowUseCase.Param(outfitId = outfitId)).asLiveData())
    }
}