package com.kaiku.cryptospot.customView.tab

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
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
import com.kaiku.cryptospot.customView.BasicType
import com.kaiku.cryptospot.customView.tab.data.BadgeTabType
import com.kaiku.cryptospot.customView.tab.data.CryptoTabType
import com.kaiku.cryptospot.presentation.theme.color_414141
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun CryptoTabComponent(
    selectedItemIndex: Int,
    items: List<BasicType>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 100.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: (BasicType) -> Unit,
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(
            easing = LinearEasing,
            durationMillis = 200
        ),
        label = ""
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
            items.mapIndexed { index, type ->
                TabItem(
                    isSelected = index == selectedItemIndex,
                    shape = shape,
                    tabWidth = tabWidth,
                    type = type,
                ) {
                    onClick(it)
                }
            }
        }
    }
}

@Composable
fun CryptoTabFillMaxWidthComponent(
    selectedItemIndex: Int,
    items: List<BasicType>,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: (BasicType) -> Unit,
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
            .fillMaxWidth()
            .clip(shape)
            .background(Color.DarkGray)
            .height(intrinsicSize = IntrinsicSize.Min)
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
            items.mapIndexed { index, type ->
                TabItem(
                    isSelected = index == selectedItemIndex,
                    shape = shape,
                    tabWidth = tabWidth,
                    type = type,
                ) {
                    onClick.invoke(it)
                }
            }
        }
    }

}

@Composable
fun CryptoTabWithBadgeComponent(
    modifier: Modifier = Modifier,
    selectedItemIndex: Int,
    items: List<BasicType>,
    tabWidth: Dp = 45.dp,
    tabHeight : Dp = 30.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: (BasicType) -> Unit,
) {

    val indicatorOffset: Dp by animateDpAsState(
        targetValue = (tabWidth + 1.dp) * selectedItemIndex,
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
                .height(tabHeight)
                .recomposeHighlighter(isCustomTab),
        ) {
            items.mapIndexed { index, type ->
                Row() {
                    if (index != 0) {
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(vertical = 5.dp)
                                .width(1.dp),
                            color = Color.Gray
                        )
                    }
                    TabItemWithBadge(
                        isSelected = index == selectedItemIndex,
                        shape = shape,
                        tabWidth = tabWidth,
                        type = type,
                        isBadgeLight = (type as BadgeTabType).isBadgeLight,
                        isEnable = type.isEnable
                    ) {
                        onClick.invoke(it)
                    }
                }

            }
        }
    }
}

@Composable
fun CryptoTabWithDividerFillMaxWidthComponent(
    modifier: Modifier = Modifier,
    selectedItemIndex: Int,
    items: List<BasicType>,
    tabHeight : Dp = 30.dp,
    shape: Shape = RoundedCornerShape(8.dp),
    onClick: (BasicType) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    var wholeWidth by remember { mutableStateOf(0.dp) }
    val tabWidth = wholeWidth / items.size


    val calculate = remember<(LayoutCoordinates) -> Unit> {
        {
            scope.launch {
                wholeWidth = with(density) {
                    it.size.width.toDp()
                }
            }
        }

    }

    val indicatorOffset: Dp by animateDpAsState(
        targetValue = (tabWidth + 1.dp) * selectedItemIndex,
        animationSpec = tween(
            easing = LinearEasing,
            durationMillis = 200
        ),
        label = ""
    )

    Box(
        modifier = modifier
            .clip(shape)
            .background(color_414141)
            .height(intrinsicSize = IntrinsicSize.Min)
            .fillMaxWidth()
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
                .height(tabHeight)
        ) {
            items.mapIndexed { index, type ->
                Row() {
                    if (index != 0) {
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(vertical = 5.dp)
                                .width(1.dp),
                            color = Color.Gray
                        )
                    }
                    TabItemWithBadge(
                        isSelected = index == selectedItemIndex,
                        shape = shape,
                        tabWidth = tabWidth,
                        type = type,
                        isBadgeLight = if (type is BadgeTabType) type.isBadgeLight else false,
                        isEnable = if (type is BadgeTabType) type.isEnable else true
                    ) {
                        onClick.invoke(it)
                    }
                }

            }
        }
    }
}

@Preview
@Composable
private fun CryptoTabPreview() {

    val currentItem = remember { mutableStateOf(CryptoTabType.getAll()[0]) }

    CryptoTabComponent(
        selectedItemIndex = currentItem.value.position,
        items = CryptoTabType.getAll(),
        onClick = {
            currentItem.value = when (it.position) {
                CryptoTabType.TabBTC.position -> CryptoTabType.TabBTC
                CryptoTabType.TabETH.position -> CryptoTabType.TabETH
                CryptoTabType.TabADA.position -> CryptoTabType.TabADA
                else -> CryptoTabType.TabBTC
            }

        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CryptoTabFillMaxWidthPreview() {

    val currentItem = remember { mutableStateOf(CryptoTabType.getAll()[0]) }

    CryptoTabFillMaxWidthComponent(
        modifier = Modifier.fillMaxWidth(),
        selectedItemIndex = currentItem.value.position,
        items = CryptoTabType.getAll(),
        onClick = {
            currentItem.value = when (it.position) {
                CryptoTabType.TabBTC.position -> CryptoTabType.TabBTC
                CryptoTabType.TabETH.position -> CryptoTabType.TabETH
                CryptoTabType.TabADA.position -> CryptoTabType.TabADA
                else -> CryptoTabType.TabBTC
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CryptoTabWithBadgePreview(){
    var currentItem by remember{ mutableStateOf(BadgeTabType.getAll()[0]) }

    CryptoTabWithBadgeComponent(
        selectedItemIndex = currentItem.position,
        items = BadgeTabType.getAll(),

        onClick = {
            currentItem = when(it.position){
                BadgeTabType.TabOne.position -> BadgeTabType.TabOne
                BadgeTabType.TabTwo.position -> BadgeTabType.TabTwo
                BadgeTabType.TabThree.position -> BadgeTabType.TabThree
                BadgeTabType.TabFour.position -> BadgeTabType.TabFour
                else -> BadgeTabType.TabOne
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun CryptoTabWithDividerFillMaxWidthComponentPreview(){
    CryptoTabWithDividerFillMaxWidthComponent(
        modifier = Modifier.fillMaxWidth(),
        selectedItemIndex = 1,
        items = CryptoTabType.getAll(),
        tabHeight = 35.dp,
        onClick = {

        }
    )
}
