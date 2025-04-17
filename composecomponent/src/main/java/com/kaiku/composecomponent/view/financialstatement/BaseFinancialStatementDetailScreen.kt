package com.kaiku.composecomponent.view.financialstatement

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_121212
import com.kaiku.composecomponent.color_272727
import com.kaiku.composecomponent.color_353535
import com.kaiku.composecomponent.color_484848
import com.kaiku.composecomponent.color_A7A7A7
import com.kaiku.composecomponent.color_F0B51C
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketParagraphText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.component.topbar.BaseTopBarComponent
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.extension.getColorAnimation
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text14Sp
import com.kaiku.composecomponent.utils.text16Sp
import com.kaiku.composecomponent.view.financialstatement.data.FinancialDetailItem
import com.kaiku.composecomponent.view.financialstatement.data.FinancialDetailViewState
import kotlinx.coroutines.launch

private const val FIRST_ITEM = 1

@Composable
fun BaseFinancialStatementDetailScreen(
    modifier: Modifier = Modifier,
    viewState: FinancialDetailViewState,
    isPreviewVisible: Boolean,
    onPreviousClick: () -> Unit,
    isNextVisible: Boolean,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val state = rememberLazyListState()

    val scope = rememberCoroutineScope()

    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            DetailTopBar(
                modifier = Modifier.heightIn(min = 44.sdp()),
                title = viewState.title,
                isPreviewVisible = isPreviewVisible,
                onPreviousClick = {
                    scope.launch {
                        state.animateScrollToItem(FIRST_ITEM)
                    }
                    onPreviousClick.invoke()
                },
                isNextVisible = isNextVisible,
                onNextClick = {
                    scope.launch {
                        state.animateScrollToItem(FIRST_ITEM)
                    }
                    onNextClick.invoke()
                },
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = modifier.padding(innerPadding),
            state = state
        ) {
            item {
                DetailTitleFlowRow(
                    viewState = viewState,
                    selectedIndex = selectedIndex,
                    onSessionClick = {
                        selectedIndex = it
                        scope.launch {
                            state.animateScrollToItem(it.coerceAtLeast(0) + FIRST_ITEM)
                        }
                    }
                )
            }

            itemsIndexed(
                items = viewState.items,
                key = { index: Int, item: FinancialDetailItem -> index }
            ) { index: Int, item: FinancialDetailItem ->

                DetailContent(
                    item = item,
                    time = if (index == 0) {
                        viewState.meetingTime
                    } else {
                        null
                    }
                )

                if (index != viewState.items.lastIndex) {
                    HorizontalDivider(
                        thickness = 8.sdp(),
                        color = color_121212
                    )
                }
            }
        }
    }
}

@Composable
private fun DetailTopBar(
    modifier: Modifier = Modifier,
    title: String,
    isPreviewVisible: Boolean,
    onPreviousClick: () -> Unit,
    isNextVisible: Boolean,
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column {
        BaseTopBarComponent(
            modifier = modifier,
            titleContent = {
                Row(
                    modifier = Modifier.fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PocketIconButton(
                        iconModifier = Modifier.size(9.sdp()),
                        drawableRes = R.drawable.lib_pocket_left_arrow,
                        isEnable = isPreviewVisible,
                        tint = getColorAnimation(
                            toColor = if (isPreviewVisible) {
                                Color.White
                            } else {
                                Color.Transparent
                            }
                        ),
                        onIconClick = {
                            if (isPreviewVisible) {
                                onPreviousClick.invoke()
                            }
                        }
                    )
                    PocketSpacer(width = 8)
                    AnimatedPocketText(
                        config = PocketTextConfig(
                            value = title,
                            style = text16Sp()
                        )
                    )
                    PocketSpacer(width = 8)
                    PocketIconButton(
                        iconModifier = Modifier.size(9.sdp()),
                        drawableRes = R.drawable.lib_pocket_right_arrow,
                        isEnable = isNextVisible,
                        tint = getColorAnimation(
                            toColor = if (isNextVisible) {
                                Color.White
                            } else {
                                Color.Transparent
                            }
                        ),
                        onIconClick = {
                            if (isNextVisible) {
                                onNextClick.invoke()
                            }
                        }
                    )
                }
            },
            onNavigationClick = onBackClick,
            background = color_272727
        )
        HorizontalDivider(
            thickness = 1.sdp(),
            color = color_353535
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailTitleFlowRow(
    modifier: Modifier = Modifier,
    viewState: FinancialDetailViewState,
    selectedIndex: Int,
    onSessionClick: (Int) -> Unit
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = color_272727
    ) {
        FlowRow(
            modifier = modifier.pocketPadding(hv = (16 to 12)),
            verticalArrangement = Arrangement.spacedBy(12.sdp()),
            horizontalArrangement = Arrangement.spacedBy(8.sdp())
        ) {
            viewState.items.forEachIndexed { index, financialDetailItem ->
                PocketTextWithClickEffect(
                    modifier = Modifier
                        .clipByShape(
                            shape = RoundedCornerShape(40.sdp()),
                            border = BorderStroke(1.sdp(), color_484848),
                            backgroundColor = if (index == selectedIndex) {
                                color_484848
                            } else {
                                Color.Transparent
                            }
                        )
                        .heightIn(min = 30.sdp())
                        .pocketPadding(hv = 16 to 4),
                    config = PocketTextConfig(
                        value = financialDetailItem.title,
                        textColor = if (index == selectedIndex) {
                            Color.White
                        } else {
                            color_484848
                        },
                        style = text16Sp()
                    ),
                    onClick = {
                        onSessionClick.invoke(index)
                    }
                )
            }
        }
    }
}

@Composable
private fun DetailContent(
    modifier: Modifier = Modifier,
    item: FinancialDetailItem,
    time: String? = null
) {
    val density = LocalDensity.current
    val fineTune = 1.sdp() // 不確定為何都有1dp的誤差
    Column(
        modifier = modifier.pocketPadding(all = 16)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PocketText(
                modifier = Modifier
                    .drawWithContent {
                        val mSize = Size(
                            width = with(density) { 3.dp.toPx() },
                            height = with(density) { 16.dp.toPx() },
                        )

                        val offsetY = (size.height - mSize.height) / 2 + fineTune.toPx()
                        drawRoundRect(
                            color = color_F0B51C,
                            topLeft = Offset(0f, offsetY),
                            size = mSize,
                            cornerRadius = CornerRadius(
                                with(density) { 2.dp.toPx() },
                                with(density) { 2.dp.toPx() }
                            )
                        )
                        drawContent()
                    }
                    .padding(start = 7.sdp()),
                config = PocketTextConfig(
                    value = item.title,
                    textColor = color_F0B51C,
                    style = text16Sp(500)
                )
            )
            time?.let {
                AnimatedPocketText(
                    config = PocketTextConfig(
                        value = "會議時間 : $it",
                        style = text14Sp(),
                        textColor = color_A7A7A7
                    )
                )
            }
        }

        PocketSpacer(height = 12)
        PocketParagraphText(
            config = PocketTextConfig(
                value = item.content,
                style = text16Sp(),
                lineHeight = dimensionResource(id = com.intuit.ssp.R.dimen._25ssp).value.sp,
                maxLines = Int.MAX_VALUE
            )
        )
    }
}