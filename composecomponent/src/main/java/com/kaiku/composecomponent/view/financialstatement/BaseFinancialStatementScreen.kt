package com.kaiku.composecomponent.view.financialstatement

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import com.intuit.ssp.R
import com.kaiku.composecomponent.color_121212
import com.kaiku.composecomponent.color_1E1E1E
import com.kaiku.composecomponent.color_272727
import com.kaiku.composecomponent.color_353535
import com.kaiku.composecomponent.color_757575
import com.kaiku.composecomponent.color_A7A7A7
import com.kaiku.composecomponent.color_F0B51C
import com.kaiku.composecomponent.component.button.PocketIconButton
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketParagraphText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text12Sp
import com.kaiku.composecomponent.utils.text14Sp
import com.kaiku.composecomponent.utils.text16Sp
import com.kaiku.composecomponent.utils.text18Sp
import com.kaiku.composecomponent.view.financialstatement.data.FinancialStatementViewState
import com.kaiku.composecomponent.view.financialstatement.data.HistoryItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BaseFinancialStatementScreen(
    modifier: Modifier = Modifier,
    viewState: FinancialStatementViewState,
    onMoreClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(color_121212),
        state = rememberLazyListState()
    ) {
        item {
            LatestOverviewContent(
                viewState = viewState,
                onMoreClick = onMoreClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(8.sdp()))
        }

        stickyHeader {
            HistoryFinancialTitle()
        }

        if (viewState.historyItems.isEmpty()) {
            item {
                PocketText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 45.sdp()),
                    config = PocketTextConfig(
                        value = "暫無資料",
                        style = text14Sp(),
                        textColor = color_757575
                    )
                )
            }
        } else {
            itemsIndexed(
                items = viewState.historyItems,
                key = { index, _ -> index }
            ) { index, item ->
                Column {

                    HistoryFinancialItem(
                        historyItem = item,
                        onItemClick = onMoreClick
                    )
                    if (index != viewState.historyItems.lastIndex) {
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.sdp()),
                            thickness = 1.sdp(),
                            color = color_353535
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LatestOverviewContent(
    modifier: Modifier = Modifier,
    viewState: FinancialStatementViewState,
    onMoreClick: () -> Unit
) {
    Column(modifier = modifier.background(color_1E1E1E)) {
        // 標題
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 57.sdp())
                .background(color_272727)
                .pocketPadding(hv = 16 to null),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                PocketText(
                    config = PocketTextConfig(
                        value = viewState.overviewState.toTitleDisplay(),
                        textColor = color_F0B51C,
                        style = text18Sp(500)
                    )
                )
                PocketSpacer(width = 8)
                if (viewState.isNewVisible) {
                    PocketText(
                        modifier = Modifier
                            .padding(top = 1.sdp()) // 不確定使用英文會有1dp差距
                            .clipByShape(
                                shape = CircleShape,
                                backgroundColor = Color.Transparent,
                                border = BorderStroke(1.sdp(), color_F0B51C)
                            )
                            .padding(
                                horizontal = 8.sdp(),
                                vertical = 2.sdp()
                            ),
                        config = PocketTextConfig(
                            value = "NEW",
                            textColor = color_F0B51C,
                            style = text12Sp(500)
                        )
                    )
                }
            }

            PocketText(
                config = PocketTextConfig(
                    value = viewState.overviewState.time,
                    textColor = color_A7A7A7,
                    style = text14Sp(500)
                )
            )
        }
        // 內容
        PocketParagraphText(
            modifier = Modifier
                .fillMaxWidth()
                .pocketPadding(all = 16),
            config = PocketTextConfig(
                value = viewState.overviewState.content,
                style = text16Sp(),
                lineHeight = dimensionResource(id = R.dimen._25ssp).value.sp,
                maxLines = Int.MAX_VALUE
            )
        )

        // 看完整
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            PocketTextWithClickEffect(
                config = PocketTextConfig(
                    value = "查看完整摘要內容",
                    textColor = color_F0B51C,
                    style = text14Sp(500)
                ),
                onClick = onMoreClick
            )
            PocketSpacer(width = 8)
            PocketIconButton(
                iconModifier = Modifier.size(20.sdp()),
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                tint = color_F0B51C,
                onIconClick = onMoreClick
            )
        }
        PocketSpacer(height = 16)
    }
}

@Composable
fun HistoryFinancialTitle(modifier: Modifier = Modifier) {
    PocketText(
        modifier = modifier
            .background(color_272727)
            .pocketPadding(start = 16)
            .heightIn(min = 46.sdp())
            .fillMaxWidth(),
        config = PocketTextConfig(
            value = "歷史財報會議",
            style = text14Sp(),
            textColor = color_A7A7A7,
            alignment = Alignment.CenterStart
        )
    )
}

@Composable
private fun HistoryFinancialItem(
    modifier: Modifier = Modifier,
    historyItem: HistoryItem,
    onItemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 46.sdp())
            .clickableEffectConfig { onItemClick.invoke() }
            .pocketPadding(hv = 16 to null),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            Modifier.pocketPadding(hv = null to 16)
        ) {
            PocketText(
                config = PocketTextConfig(
                    value = historyItem.toDisplay(),
                    style = text18Sp(500)
                )
            )
            PocketSpacer(height = 12)
            PocketText(
                config = PocketTextConfig(
                    value = historyItem.date,
                    style = text16Sp(500),
                    textColor = color_A7A7A7
                )
            )
        }
        PocketIconButton(
            iconModifier = Modifier.size(28.sdp()),
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            tint = Color.White,
            onIconClick = onItemClick
        )
    }
}