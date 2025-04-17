package com.kaiku.composecomponent.view.mission

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_414141
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.color_c8c9ca
import com.kaiku.composecomponent.component.loading.WithHoverLoading
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.topbar.PocketAppBarWithBackNavigation
import com.kaiku.composecomponent.utils.basic.getScreenWidth
import com.kaiku.composecomponent.utils.isPreviewMode
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text13Sp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.view.mission.data.MissionStringRes
import com.kaiku.composecomponent.view.mission.data.MissionViewAction
import com.kaiku.composecomponent.view.mission.data.MissionViewState
import com.kaiku.composecomponent.view.mission.ui.AwardCollectionScene
import com.kaiku.composecomponent.view.mission.ui.MissionCard
import com.kaiku.composecomponent.view.mission.ui.MissionCardAnimationConfig

@Composable
fun BaseMissionScreen(
    stringRes: MissionStringRes = MissionStringRes.Default,
    viewState: MissionViewState,
    action: (MissionViewAction) -> Unit,
    onFinish: () -> Unit
) {
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            PocketAppBarWithBackNavigation(
                modifier = Modifier.height(44.sdp()),
                title = stringRes.missionTopBarTitle,
                background = color_333333,
                onBackClick = { onFinish.invoke() }
            )
        },
        content = { innerPadding ->
            val isLoading = viewState.isShowLoading
            WithHoverLoading(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                alpha = 0f,
                isLoading = isLoading,
                content = {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        state = listState,
                    ) {

                        // 結算文案
                        item {
                            SettlementDescription(viewState = viewState)
                        }

                        //背包
                        item {
                            AwardCollectionScene(
                                stringRes = stringRes,
                                viewState = viewState,
                                action = action
                            )
                        }

                        //任務列表
                        if (isLoading.not()) {
                            lazyMissionCard(
                                listState = listState,
                                stringRes = stringRes,
                                viewState = viewState,
                                action = action
                            )
                        }

                    }
                }
            )

        }
    )
}

@Composable
private fun SettlementDescription(
    modifier: Modifier = Modifier,
    viewState: MissionViewState,
) {
    val dynamicText = if(viewState.isShowLoading || viewState.hint.isEmpty()) {
        ""
    } else {
        "※ ${viewState.hint}"
    }

    AnimatedPocketText(
        modifier = modifier
            .padding(
                top = 8.sdp(),
                start = 16.sdp(),
                end = 16.sdp(),
            )
            .fillMaxWidth(),
        config = PocketTextConfig(
            value = dynamicText,
            style = text13Sp(),
            textColor = color_9e9e9f,
            alignment = Alignment.CenterEnd,
            maxLines = Int.MAX_VALUE
        ),
        defaultColor = color_9e9e9f
    )
}

private fun LazyListScope.lazyMissionCard(
    listState: LazyListState,
    stringRes: MissionStringRes,
    viewState: MissionViewState,
    action: (MissionViewAction) -> Unit
) {
    val missions = viewState.missionList

    if (missions.isEmpty()) {
        item {
            EmptyMissionScene(stringRes = stringRes)
        }
    } else {
        itemsIndexed(
            items = missions,
            key = { _, state -> state.missionId }
        ) { index, state ->
            // 利用listState捕捉顯示中的item，來敲動卡片進入螢幕的動畫
            LaunchedEffect(listState, state.isVisible) {
                snapshotFlow { listState.layoutInfo to state }
                    .collect { (layout, state) ->
                        val isVisible = layout.visibleItemsInfo.any { info ->
                            info.key == state.missionId
                        }

                        // 在原本非可視的卡片，並且當使用者滑動觸發顯示時，才會觸發滑入action
                        if (state.isVisible.not() && isVisible) {
//                            Timber.e("dispatch [${state.title}] visible action")
                            action.invoke(
                                MissionViewAction.MissionCardVisibleAction(
                                    index = index,
                                    isVisible = true // 顯示後不再隱藏
                                )
                            )
                        }
                    }
            }

            val aniConfig = if (isPreviewMode()) {
                MissionCardAnimationConfig.PREVIEW
            } else {
                MissionCardAnimationConfig(
                    isVisible = state.isVisible,
                    slideInOffset = if (state.isVisible) {
                        0.dp
                    } else {
                        getScreenWidth()
                    }
                )
            }

            MissionCard(
                stringRes = stringRes,
                cardState = state,
                aniConfig = aniConfig,
                onExpandedClick = {
                    action.invoke(
                        MissionViewAction.MissionCardExpandAction(index)
                    )
                },
                onRewardClick = {
                    action.invoke(
                        MissionViewAction.OnRewardClickAction(index, it)
                    )
                },
                onGotoMyCoupon = {
                    action.invoke(
                        MissionViewAction.OnGotoMyCouponAction(it)
                    )
                }
            )

        }

        // 任務列表不需要分類了幫ＱＱ
//        missions.groupBy { it.missionCategory == MissionCategory.STEP}
//            .forEach { (isStep, list) ->
                // 分類標題是否sticky
//            stickyHeader {
//                MissionStickyHeader(isStepMission = isStep)
//            }
                //不需要header了幫ＱＱ
//                item {
//                    MissionStickyHeader(isStepMission = isStep)
//                }
//            }
    }
}

@Composable
private fun MissionStickyHeader(
    modifier: Modifier = Modifier,
    isStepMission: Boolean,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.sdp(), vertical = 6.sdp()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = color_414141
        )
        Spacer(modifier = Modifier.width(15.sdp()))
        PocketText(
            modifier = Modifier.height(18.sdp()),
            config = PocketTextConfig(
                value = if (isStepMission) "階段任務" else "一般任務",
                textColor = color_c8c9ca,
                style = text13Sp(),
                maxLines = 1
            )
        )
        Spacer(modifier = Modifier.width(15.sdp()))
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = color_414141
        )
    }
}

@Composable
private fun EmptyMissionScene(
    modifier: Modifier = Modifier,
    stringRes: MissionStringRes
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(143.sdp()))
            Image(
                modifier = Modifier.size(70.sdp()),
                painter = painterResource(id = R.drawable.pocket_ic_mission_empty_list),
                contentDescription = null
            )
            Spacer(modifier = Modifier.size(4.sdp()))
            Box(
                contentAlignment = Alignment.Center
            ) {
                PocketText(
                    config = PocketTextConfig(
                        value = stringRes.missionEmptyList,
                        style = text15Sp().copy(
                            lineHeight = dimensionResource(id = com.intuit.sdp.R.dimen._16sdp).value.sp
                        ),
                        textColor = Color.White,
                        textAlign = TextAlign.Center,
                        maxLines = 2
                    )
                )
            }
        }
    }
}