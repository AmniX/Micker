package com.amnix.micker.domain.usecase

import com.amnix.micker.data.ReactiveCursor
import com.amnix.micker.domain.entity.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class LoadImagesUseCaseImpl @Inject constructor(
    private val reactiveCursor: ReactiveCursor
) : LoadImagesUseCase {
    override suspend fun execute(input: LoadImagesUseCase.Input): Flow<Media.Image> = withContext(Dispatchers.IO) {
        reactiveCursor.findImages()
    }
}