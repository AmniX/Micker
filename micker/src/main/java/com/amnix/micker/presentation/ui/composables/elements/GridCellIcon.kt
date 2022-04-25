package com.amnix.micker.presentation.ui.composables.elements

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun GridCellIcon(
    modifier: Modifier = Modifier,
    gridCellRange: IntRange = 2..5,
    currentCellCount: MutableState<Int> = remember { mutableStateOf(3) },
    size: Dp = 24.dp,
    color: Color = Color.Gray,
    onSpanCountChanged: (newCount: Int) -> Unit = {}
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(currentCellCount.value),
        modifier = modifier.size(size)
    ) {
        items(currentCellCount.value * currentCellCount.value) {
            Box(
                modifier = Modifier
                    .size(size.div(currentCellCount.value))
                    .padding(1.dp)
                    .animateItemPlacement()
                    .animateContentSize()
                    .background(color)
                    .clickable {
                        currentCellCount.value =
                            currentCellCount.value.modifySpanCount(range = gridCellRange)
                        onSpanCountChanged(currentCellCount.value)
                    }
            )
        }
    }
}

private fun Int.modifySpanCount(range: IntRange) =
    if (this == range.last) range.first else this.inc()
