package com.amnix.micker.domain.entity

sealed interface Media {
    val filePath: String

    data class Image(override val filePath: String, val isSelected: Boolean = false) : Media
}
