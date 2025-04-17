package com.kaiku.composecomponent.view.guide

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.color_353535
import com.kaiku.composecomponent.color_757575
import com.kaiku.composecomponent.color_f19dac
import com.kaiku.composecomponent.component.pager.GuideAction
import com.kaiku.composecomponent.component.pager.PageType
import com.kaiku.composecomponent.component.pager.PocketHorizontalPager
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.text15Sp
import kotlinx.coroutines.launch

@Composable
fun BaseGuideScreen(
    modifier: Modifier,
    guideList: List<PageType>,
    action: (GuideAction) -> Unit,
    coverContent: @Composable BoxScope.(Int) -> Unit
) {
    val pagerState = rememberPagerState { guideList.size }
    val scope = rememberCoroutineScope()
    val pageChangeAniSpec: AnimationSpec<Float> by remember {
        mutableStateOf(
            tween(
                durationMillis = 300,
                easing = LinearEasing
            )
        )
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            val currentPage = guideList.getOrNull(page)
            if (currentPage != null && currentPage == PageType.AutoClosePage) {
                action.invoke(GuideAction.Close)
            }
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        PocketHorizontalPager(
            pagerState = pagerState,
            isClickNavigation = true,
            action = action
        ) { page ->
            val currentPage = guideList.getOrNull(page)
            if (currentPage == null) {
                action.invoke(GuideAction.ForceClose)
            } else {
                GuidePage(
                    pageType = currentPage,
                    action = {
                        scope.launch {
                            when (it) {
                                GuideAction.Next -> {
                                    pagerState.animateScrollToPage(
                                        page = page + 1,
                                        animationSpec = pageChangeAniSpec
                                    )
                                }

                                GuideAction.Previous -> {
                                    pagerState.animateScrollToPage(
                                        page = page - 1,
                                        animationSpec = pageChangeAniSpec
                                    )
                                }

                                else -> action.invoke(it)
                            }
                        }
                    }
                )
            }
        }
        this@Box.coverContent(pagerState.currentPage)

    }

}

@Composable
fun GuidePage(
    pageType: PageType,
    action: (GuideAction) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color_757575)
    ) {

        when (pageType) {
            is PageType.DefaultPage -> {
                Image(
                    painter = painterResource(pageType.image),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }

            is PageType.LastPage -> {
                Image(
                    painter = painterResource(pageType.image),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                Box(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .clickableEffectConfig(
                        config = ClickableConfig.SILENCE
                    ) { action.invoke(GuideAction.Close) })
            }

            PageType.AutoClosePage -> {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = color_353535
                ) { }

                PocketText(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    config = PocketTextConfig(
                        value = "AutoClosePage"
                    )
                )
            }
        }
    }

}
