package com.amnix.micker.di

import com.amnix.micker.data.ReactiveCursor
import com.amnix.micker.data.ReactiveCursorImpl
import com.amnix.micker.domain.usecase.LoadImagesUseCase
import com.amnix.micker.domain.usecase.LoadImagesUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class MickerModule {

    @Binds
    abstract fun bindReactiveCursor(reactiveCursorImpl: ReactiveCursorImpl): ReactiveCursor

    @Binds
    abstract fun bindImageUseCase(imagesUseCaseImpl: LoadImagesUseCaseImpl): LoadImagesUseCase
}