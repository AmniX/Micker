package com.amnix.micker.presentation.ui.composables.screen

import android.graphics.Bitmap
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.amnix.micker.presentation.MickerViewModel
import com.amnix.micker.presentation.ui.composables.elements.GridCellIcon

@Composable
fun MickerScreen(
    initialCellCount: Int = 3,
    backgroundColor: Color = MaterialTheme.colors.background,
    imageBackgroundColor: Color = MaterialTheme.colors.onBackground.copy(alpha = .12f),
    titleBarColor: Color = backgroundColor,
    crossFadeImages: Boolean = true,
    imageScale: ContentScale = ContentScale.Crop,
    imageAspectRatio: Float = 1f,
    paddingAroundCells: Dp = 2.dp,
    imageShape: Shape = RoundedCornerShape(paddingAroundCells),
) {
    val viewModel = hiltViewModel<MickerViewModel>()
    val images by viewModel.images.collectAsState()
    val density = LocalDensity.current
    val cellCount = remember { mutableStateOf(initialCellCount) }
    val cellWidth = LocalConfiguration.current.screenWidthDp
        .times(density.density)
        .div(other = cellCount.value)
        .toInt()
    val onBackPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Scaffold(topBar = {
        TopAppBar(elevation = 4.dp, backgroundColor = titleBarColor) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colors.onBackground,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        onBackPressedDispatcher?.onBackPressed()
                    })
            Spacer(modifier = Modifier.weight(1f))
            GridCellIcon(modifier = Modifier.padding(end = 16.dp), currentCellCount = cellCount)

        }
    }) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(count = cellCount.value),
            contentPadding = PaddingValues(all = paddingAroundCells.div(other = 2)),
            modifier = Modifier.background(backgroundColor)
        ) {
            itemsIndexed(images) { index, image ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement()
                        .aspectRatio(imageAspectRatio)
                        .padding(
                            paddingAroundCells
                                .div(other = 2)
                                .plus(if (image?.isSelected == true) 4.dp else 0.dp)
                        )
                        .background(imageBackgroundColor)
                ) {
                    if (image?.filePath != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(image.filePath)
                                .bitmapConfig(Bitmap.Config.RGB_565)
                                .size(cellWidth)
                                .crossfade(enable = crossFadeImages).build(),
                            contentDescription = null,
                            contentScale = imageScale,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(imageShape)
                        )
                    }
                }
            }
        }
    }
    LaunchedEffect(key1 = viewModel) {
        viewModel.loadImages()
    }
}