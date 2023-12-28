package com.kaiku.cryptospot.presentation.test

import android.content.Context
import android.media.AudioManager
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState
import org.burnoutcrew.reorderable.reorderable
import timber.log.Timber

@Composable
fun ReorderGrid(vm: ReorderListViewModel = viewModel()) {
    Column {
//        HorizontalGrid(
//            vm = vm,
//            modifier = Modifier.padding(vertical = 16.dp)
//        )
        VerticalGrid(vm = vm)
    }
}

@Composable
private fun HorizontalGrid(
    vm: ReorderListViewModel,
    modifier: Modifier = Modifier,
) {
    val state = rememberReorderableLazyGridState(onMove = vm::moveCat)
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        state = state.gridState,
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .reorderable(state)
            .height(200.dp)
            .detectReorderAfterLongPress(state)
    ) {
        items(vm.cats, { it.key }) { item ->
            ReorderableItem(state, item.key) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 16.dp else 0.dp)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .shadow(elevation.value)
                        .aspectRatio(1f)
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Text(item.title)
                }
            }
        }
    }
}

@Composable
private fun VerticalGrid(
    vm: ReorderListViewModel,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val state = rememberReorderableLazyGridState(
        onMove = vm::moveDog,
        canDragOver = vm::isDogDragEnabled
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier.reorderable(state),
        state = state.gridState,
        contentPadding = PaddingValues(horizontal = 28.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(vm.dogs, { it.key }) { item ->
            if (item.isLocked) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .background(Color.Red)
                ) {
                    Text(item.title)
                }
            } else {
                ReorderableItem(state, item.key) { isDragging ->
                    val elevation = animateDpAsState(if (isDragging) 8.dp else 0.dp, label = "")
                    val bg = animateColorAsState(
                        targetValue = if (isDragging) Color.Blue else MaterialTheme.colorScheme.primary,
                        label = ""
                    )

                    var isItemDragging by remember {
                        mutableStateOf(false)
                    }
                    isItemDragging = isDragging
                    LaunchedEffect(isItemDragging){
                        if (isItemDragging) {
                            Timber.tag("wtf").e("some one isDragging")
                            (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager)
                                .playSoundEffect(AudioManager.FX_KEY_CLICK)

                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        } else {
                            Timber.tag("wtf").e("not drag...")
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .detectReorderAfterLongPress(state)
                            .shadow(elevation.value)
                            .aspectRatio(1f)
                            .background(bg.value)
                    ) {
                        Text(item.title)
                    }
                }
            }
        }
    }
}
