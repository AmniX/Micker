package com.amnix.micker.domain.usecase

import com.amnix.micker.domain.entity.Media
import kotlinx.coroutines.flow.Flow

interface LoadImagesUseCase : BaseUseCase<LoadImagesUseCase.Input, Flow<Media.Image>> {
    data class Input(val bunchSize: Int = 1)
}