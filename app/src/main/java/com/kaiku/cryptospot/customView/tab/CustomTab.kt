package com.kaiku.cryptospot.customView.tab

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.cryptospot.common.Debug.isCustomTab
import com.kaiku.cryptospot.common.recomposeHighlighter
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 100.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: (index: Int) -> Unit,
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(
            easing = LinearEasing,
            durationMillis = 200
        ),
    )

    Box(
        modifier = modifier
            .clip(shape)
            .background(Color.DarkGray)
            .height(intrinsicSize = IntrinsicSize.Min)
            .recomposeHighlighter(isCustomTab),
    ) {
        MyTabIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = Color.White,
            shape = shape
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(shape)
                .recomposeHighlighter(isCustomTab),
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                MyTabItem(
                    isSelected = isSelected,
                    shape = shape,
                    tabWidth = tabWidth,
                    text = text,
                ) {
                    onClick(index)
                }
            }
        }
    }
}

@Composable
fun CustomTabFillMaxWidth(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: (index: Int) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    var wholeWidth by remember { mutableStateOf(0.dp) }
    val tabWidth = wholeWidth / items.size

    val calculate: (LayoutCoordinates) -> Unit = remember {
        {
            scope.launch {
                wholeWidth = with(density) {
                    it.size.width.toDp()
                }
                if (isCustomTab) Timber.e("width : $wholeWidth")
            }
        }
    }


    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(
            easing = LinearEasing,
            durationMillis = 200
        ),
    )

    Box(
        modifier = modifier
            .clip(shape)
            .background(Color.DarkGray)
            .height(intrinsicSize = IntrinsicSize.Min)
            .fillMaxWidth()
            .recomposeHighlighter(isCustomTab)
            .onGloballyPositioned {
                calculate.invoke(it)
            },
    ) {
        MyTabIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = Color.White,
            shape = shape
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(shape)
                .recomposeHighlighter(isCustomTab),
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                MyTabItem(
                    isSelected = isSelected,
                    shape = shape,
                    tabWidth = tabWidth,
                    text = text,
                ) {
                    onClick(index)
                }
            }
        }
    }


}

@Preview
@Composable
private fun CustomTabFillMaxWidthPreview() {

    val index = remember { mutableStateOf(0) }

    CustomTabFillMaxWidth(
        modifier = Modifier.fillMaxWidth(),
        selectedItemIndex = index.value,
        items = listOf("一般單", "觸價單"),
        onClick = {
            index.value = it
        }
    )
}

@Preview
@Composable
private fun CustomTabPreview() {

    val index = remember { mutableStateOf(0) }

    CustomTab(
        selectedItemIndex = index.value,
        items = listOf("一般單", "觸價單"),
        onClick = {
            index.value = it
        }
    )
}
