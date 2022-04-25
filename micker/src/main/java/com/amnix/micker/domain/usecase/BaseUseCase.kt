package com.amnix.micker.domain.usecase

interface BaseUseCase<Input, Output> {
    suspend fun execute(input: Input): Output
}