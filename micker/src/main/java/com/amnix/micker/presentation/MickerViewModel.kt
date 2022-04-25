package com.amnix.micker.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amnix.micker.domain.entity.Media
import com.amnix.micker.domain.usecase.LoadImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_BUNCH_SIZE = 20
private const val PLACEHOLDER_COUNTS = 100

@HiltViewModel
class MickerViewModel @Inject constructor(
    private val loadImagesUseCase: LoadImagesUseCase
) : ViewModel() {
    private val _images = MutableStateFlow(arrayOfNulls<Media.Image?>(PLACEHOLDER_COUNTS))
    val images: StateFlow<Array<Media.Image?>>
        get() = _images.asStateFlow()

    fun loadImages() {
        viewModelScope.launch {
            loadImagesUseCase.execute(LoadImagesUseCase.Input(bunchSize = DEFAULT_BUNCH_SIZE))
                .withIndex().onEach { newData ->
                    _images.update {
                        when {
                            newData.index < PLACEHOLDER_COUNTS -> it.apply {
                                this[newData.index] = newData.value
                            }
                            else -> it + newData.value
                        }
                    }

                }.onCompletion {

                }.launchIn(this)
        }
    }

    fun select(index: Int) = _images.update {
        it.apply {
            it[index] = it[index]?.copy(isSelected = true)
        }
    }

    fun unselect(index: Int) = _images.update {
        it.apply {
            it[index] = it[index]?.copy(isSelected = false)
        }
    }
}