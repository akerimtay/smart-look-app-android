package com.akerimtay.smartwardrobe.user.domain

import android.graphics.Bitmap
import com.akerimtay.smartwardrobe.common.base.UseCase
import com.akerimtay.smartwardrobe.user.domain.gateway.UserRemoteGateway
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class UploadImageUseCase(
    private val userRemoteGateway: UserRemoteGateway
) : UseCase<UploadImageUseCase.Param, String>() {
    override val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override suspend fun execute(parameters: Param): String =
        userRemoteGateway.uploadImage(fileName = parameters.fileName, bitmap = parameters.bitmap)

    data class Param(val fileName: String, val bitmap: Bitmap)
}