package com.kaiku.composecomponent.component.pager

import android.os.Parcelable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Composable
fun PocketHorizontalPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    isClickNavigation: Boolean = false,
    @ColorRes backgroundColor: Color = Color.Transparent,
    action: (GuideAction) -> Unit,
    content: @Composable (Int) -> Unit,
) {
    HorizontalPager(
        modifier = modifier,
        state = pagerState
    ) { page ->
        Box(modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(Unit) {
                if (isClickNavigation) {
                    detectTapGestures { offset ->
                        if (offset.x < size.width / 2) {
                            action.invoke(GuideAction.Previous)
                        } else {
                            action.invoke(GuideAction.Next)
                        }
                    }
                }
            }) {

            content.invoke(page)
        }
    }
}

/**
 * 介紹頁動作
 * @property Next 下一頁
 * @property Previous 前一頁
 * @property Close 關閉頁面
 * @property ForceClose 強制關閉頁面(可不更新sharePreferences用於下次打開再次顯示介紹頁)
 */
sealed class GuideAction {
    data object Next : GuideAction()
    data object Previous : GuideAction()
    data object Close : GuideAction()
    data object ForceClose : GuideAction()
}

/**
 * 頁面的種類
 * @property DefaultPage 一般畫面
 * @property LastPage 最後頁
 * @property AutoClosePage 最後頁後，用於顯示即關閉
 */
sealed class PageType: Parcelable {
    @Parcelize
    data class DefaultPage(
        @DrawableRes val image: Int
    ) : PageType()

    @Parcelize
    data class LastPage(
        @DrawableRes val image: Int
    ) : PageType()

    @Parcelize
    data object AutoClosePage : PageType()
}

