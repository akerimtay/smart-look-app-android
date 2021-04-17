package com.akerimtay.smartwardrobe.outfit.data.api

import com.akerimtay.smartwardrobe.outfit.data.OutfitConverter
import com.akerimtay.smartwardrobe.outfit.domain.OutfitRemoteGateway
import com.akerimtay.smartwardrobe.outfit.model.Outfit
import com.akerimtay.smartwardrobe.outfit.model.OutfitGender
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

private const val OUTFITS = "outfits"
private const val ID = "id"
private const val GENDER = "gender"

class OutfitService(
    private val database: FirebaseFirestore,
) : OutfitRemoteGateway {
    override suspend fun loadOutlets(gender: OutfitGender, after: Long?, limit: Long): List<Outfit> {
        val query = database.collection(OUTFITS)
            .whereEqualTo(GENDER, gender.serializedName)
            .orderBy(ID)
            .limit(limit)
        val task = after?.let { query.startAfter(after).get() } ?: query.get()
        val response = task.await().toObjects(OutfitResponse::class.java)
        return OutfitConverter.fromNetwork(response)
    }
}