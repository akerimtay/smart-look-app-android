package com.akerimtay.smartwardrobe.outfit.data

import com.akerimtay.smartwardrobe.outfit.data.api.OutfitResponse
import com.akerimtay.smartwardrobe.outfit.data.db.OutfitEntity
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.akerimtay.smartwardrobe.outfit.model.Season
import java.util.*

object OutfitConverter {
    fun fromNetwork(response: OutfitResponse): Outfit =
        Outfit(
            id = response.id ?: 0,
            name = response.name.orEmpty(),
            gender = OutfitGender.toGender(response.gender),
            season = Season.toSeason(response.season),
            temperatureFrom = response.temperatureFrom ?: 0,
            temperatureTo = response.temperatureTo ?: 0,
            createdDate = response.createdDate ?: Date(),
            imageUrl = response.imageUrl.orEmpty(),
            sourceUrl = response.sourceUrl.orEmpty(),
        )

    fun fromNetwork(responses: List<OutfitResponse>): List<Outfit> = responses.map { fromNetwork(it) }

    fun toNetwork(request: Outfit): OutfitResponse =
        OutfitResponse(
            id = request.id,
            name = request.name,
            gender = request.gender.serializedName,
            season = request.season.serializedName,
            temperatureFrom = request.temperatureFrom,
            temperatureTo = request.temperatureTo,
            createdDate = request.createdDate,
            imageUrl = request.imageUrl,
            sourceUrl = request.sourceUrl
        )

    fun toNetwork(requests: List<Outfit>): List<OutfitResponse> = requests.map { toNetwork(it) }

    fun toDatabase(model: Outfit): OutfitEntity =
        OutfitEntity(
            id = model.id,
            name = model.name,
            gender = model.gender,
            season = model.season,
            temperatureFrom = model.temperatureFrom,
            temperatureTo = model.temperatureTo,
            createdDate = model.createdDate,
            imageUrl = model.imageUrl,
            sourceUrl = model.sourceUrl,
        )

    fun toDatabase(models: List<Outfit>): List<OutfitEntity> = models.map { toDatabase(it) }

    fun fromDatabase(entity: OutfitEntity): Outfit =
        Outfit(
            id = entity.id,
            name = entity.name,
            gender = entity.gender,
            season = entity.season,
            temperatureFrom = entity.temperatureFrom,
            temperatureTo = entity.temperatureTo,
            createdDate = entity.createdDate,
            imageUrl = entity.imageUrl,
            sourceUrl = entity.sourceUrl,
        )

    fun fromDatabase(entities: List<OutfitEntity>): List<Outfit> = entities.map { fromDatabase(it) }
}