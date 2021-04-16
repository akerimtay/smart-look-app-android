package com.akerimtay.smartwardrobe.outfit.data

import com.akerimtay.smartwardrobe.outfit.data.api.OutfitResponse
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.outfit.model.Season
import java.util.Date

object OutfitConverter {
    fun fromNetwork(response: OutfitResponse): Outfit =
        Outfit(
            id = response.id.orEmpty(),
            name = response.name.orEmpty(),
            gender = OutfitGender.toGender(response.gender),
            season = Season.toGender(response.season),
            temperatureFrom = response.temperatureFrom ?: 0,
            temperatureTo = response.temperatureTo ?: 0,
            createdDate = response.createdDate ?: Date()
        )

    fun fromNetwork(responses: List<OutfitResponse>): List<Outfit> = responses.map { fromNetwork(it) }
}