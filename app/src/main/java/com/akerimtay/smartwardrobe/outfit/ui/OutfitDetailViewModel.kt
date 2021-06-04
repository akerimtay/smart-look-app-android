package com.akerimtay.smartwardrobe.outfit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.akerimtay.smartwardrobe.common.base.Action
import com.akerimtay.smartwardrobe.common.base.BaseViewModel
import com.akerimtay.smartwardrobe.common.base.SingleLiveEvent
import com.akerimtay.smartwardrobe.common.base.adapter.BaseContentItem
import com.akerimtay.smartwardrobe.content.ItemContentType
import com.akerimtay.smartwardrobe.content.item.OutfitDetailItem
import com.akerimtay.smartwardrobe.outfit.domain.GetOutfitByIdAsFlowUseCase
import com.akerimtay.smartwardrobe.outfit.domain.GetSimilarOutfitsUseCase
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import kotlinx.coroutines.flow.map

private const val LIMIT = 10L

class OutfitDetailViewModel(
    private val outfitId: Long,
    private val getOutfitByIdAsFlowUseCase: GetOutfitByIdAsFlowUseCase,
    private val getSimilarOutfitsUseCase: GetSimilarOutfitsUseCase
) : BaseViewModel() {
    private val _actions = SingleLiveEvent<OutfitDetailAction>()
    val actions: LiveData<OutfitDetailAction> = _actions

    val outfit: LiveData<Outfit?> = liveData {
        emitSource(getOutfitByIdAsFlowUseCase(GetOutfitByIdAsFlowUseCase.Param(outfitId = outfitId)).asLiveData())
    }
    val similarOutfits = outfit.switchMap { data ->
        liveData {
            data?.let { data ->
                getSimilarOutfitsUseCase(
                    GetSimilarOutfitsUseCase.Param(
                        id = outfitId,
                        season = data.season,
                        gender = data.gender,
                        limit = LIMIT
                    )
                ).map { list ->
                    list.map {
                        OutfitDetailItem(
                            outfit = it,
                            onItemClickListener = {
                                _actions.postValue(OutfitDetailAction.ShowSimilarOutfit(outfitId = it.id))
                            }
                        ) as BaseContentItem<ItemContentType>
                    }
                }.asLiveData()
            }?.let { emitSource(it) }
        }
    }
}

sealed class OutfitDetailAction : Action {
    data class ShowSimilarOutfit(val outfitId: Long) : OutfitDetailAction()
}