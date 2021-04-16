package com.akerimtay.smartwardrobe.outfit.data.api

import com.akerimtay.smartwardrobe.outfit.data.OutfitConverter
import com.akerimtay.smartwardrobe.outfit.domain.OutfitRemoteGateway
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val OUTFITS = "outfits"
private const val ID = "id"

class OutfitService(
    private val database: FirebaseFirestore
) : OutfitRemoteGateway {
    override suspend fun loadOutlets(gender: OutfitGender, after: String?, limit: Long): List<Outfit> {
        val query = database.collection(OUTFITS)
            .orderBy(ID)
            .limit(limit)
        after?.let { query.startAfter(after) }
        val response = query.get().await().toObjects(OutfitResponse::class.java)
        return OutfitConverter.fromNetwork(response).filter { it.gender == gender }
    }
}