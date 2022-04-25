package com.amnix.micker.data

import androidx.annotation.WorkerThread
import com.amnix.micker.domain.entity.Media
import kotlinx.coroutines.flow.Flow

internal interface ReactiveCursor {
    @WorkerThread
    fun findImages(): Flow<Media.Image>
}