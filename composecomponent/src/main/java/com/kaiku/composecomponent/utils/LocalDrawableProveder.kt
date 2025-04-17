package com.kaiku.composecomponent.utils

import androidx.compose.runtime.Composable
import com.kaiku.composecomponent.model.drawableProvider.DrawableProvider
import com.kaiku.composecomponent.model.drawableProvider.PreviewDrawableProvider
import org.koin.androidx.compose.get

/**
 * drawableRes 提供者
 * 若是在預覽模式下，則使用預覽，
 * 在build的狀態下，則使用Koin的依賴
 *
 * @param customProvider 自定義的 drawableRes 提供者
 */
@Composable
fun localDrawableProvider(
    customProvider: DrawableProvider? = null
): DrawableProvider {
    return if (isPreviewMode()) {
        customProvider?.let {
            customProvider
        } ?: run {
            PreviewDrawableProvider()
        }
    } else {
        get<DrawableProvider>()
    }
}