package com.kaiku.composecomponent.extension

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaiku.composecomponent.LocalProvider.LocalSpacerIndicator
import com.kaiku.composecomponent.color_shadow
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.utils.isPreviewMode
import com.kaiku.composecomponent.utils.sdp

/**
 * onClick的點擊extension
 *
 * @param config 點擊設定
 * @param onScaling export縮放事件 (用於子類點擊縮放父類)
 * @param onClick export點擊事件
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clickableEffectConfig(
    config: ClickableConfig = ClickableConfig.DEFAULT,
    onScaling: ((Float) -> Unit)? = null,
    onClick: () -> Unit
) = composed(

    factory = {
        val context = LocalContext.current
        val haptic = LocalHapticFeedback.current
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val scale by animateFloatAsState(
            targetValue = if (isPressed) 0.9f else 1f,
            label = "scale"
        )
        LaunchedEffect(scale) {
            onScaling?.invoke(scale)
        }

        val rememberRipple = remember(config) {
            ripple(
                bounded = config.bounded,
                radius = config.rippleRadius,
                color = config.rippleColor
            )
        }

        this.then(
            Modifier
                .scale(if (config.clickEffect) scale else 1f)
                .clickable(
                    interactionSource = interactionSource,
                    indication = if (config.needRipple) rememberRipple else null,
                    onClick = {
                        onClick.withEffect(
                            context = context,
                            haptic = haptic,
                            needSound = config.needSound,
                            needHaptic = config.needHaptic
                        ).invoke()
                    }
                )
        )
    }
)

/**
 * 依照形狀裁切元件並設置背景
 *
 * @param shape 填入形狀
 * @param backgroundColor 元件背景顏色
 * @param border 外框顏色
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.clipByShape(
    shape: Shape,
    backgroundColor: Color = Color.Transparent,
    border: BorderStroke = BorderStroke(0.dp, Color.Transparent)
) = composed(
    factory = {
        this.then(
            Modifier
                .clip(shape)
                .background(backgroundColor, shape)
                .border(border, shape)
        )
    }
)

/**
 * 自定義依照手機螢幕調整適當元件高度比例
 *
 * @param figmaHeight 填入 FIGMA 給的高度
 * @param isUnderSizeConstant 當希望維持最小的大小 ➔ true, 希望自適應的狀態 ➔ false,
 */

fun Modifier.ratioHeight(
    figmaHeight: Dp,
    isUnderSizeConstant: Boolean = true // 預設為維持最小大小, 類似 min Height的感覺
) = composed(
    factory = {
        val defaultHeight = 812 // figma 預設高度
        val configuration = LocalConfiguration.current

        val screenRatio by remember {
            mutableFloatStateOf(
                (configuration.screenHeightDp.dp / defaultHeight).value
            )
        }

        this.then(
            Modifier.height(
                if (isUnderSizeConstant && screenRatio < 1) {
                    figmaHeight
                } else {
                    figmaHeight * screenRatio
                }
            )
        )
    }
)

/**
 *  @param ratio 螢幕比例
 *  @param isMin 輸入的螢幕比例為最大高度或是最小高度
 *  @param fixed 是否固定高度
 */
fun Modifier.screenHeight(
    ratio: Float? = null,
    isMin: Boolean = false,
    fixed: Boolean = true
) = composed(
    factory = {
        ratio?.let { mRatio ->
            val configuration = LocalConfiguration.current

            val height by rememberUpdatedState(
                (configuration.screenHeightDp.dp * mRatio).value
            )

            if (fixed) {
                // 固定高度
                this.then(Modifier.height(height.dp))
            } else {
                if (isMin) {
                    // 輸入比例為最小高度
                    this.then(Modifier.heightIn(min = height.dp))
                } else {
                    // 輸入比例為最大高度
                    this.then(Modifier.heightIn(max = height.dp))
                }
            }
        } ?: run {
            // 完全自適應
            this
        }
    }
)

/**
 * @param figmaSize 填入 FIGMA 給的高度
 */
fun Modifier.ratioSize(
    figmaSize: Dp
) = composed(
    factory = {
        val figmaScreenWidth = 375 // figma 預設寬度
        val configuration = LocalConfiguration.current

        val ratio by remember {
            mutableFloatStateOf(
                (figmaSize / figmaScreenWidth).value
            )
        }

        this.then(
            Modifier.size(configuration.screenWidthDp.dp * ratio)
        )
    }
)

fun Modifier.advanceShadow(
    color: Color = Color.Black,
    borderRadius: Dp = 16.dp,
    blurRadius: Dp = 16.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Float = 1f,
) = drawBehind {
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        val spreadPixel = spread.dp.toPx()
        val leftPixel = (0f - spreadPixel) + offsetX.toPx()
        val topPixel = (0f - spreadPixel) + offsetY.toPx()
        val rightPixel = (this.size.width)
        val bottomPixel = (this.size.height + spreadPixel)

        if (blurRadius != 0.dp) {
            // The feature maskFilter used below to apply the blur effect only works with hardware acceleration disabled.
            frameworkPaint.maskFilter =
                (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
        }

        frameworkPaint.color = color.toArgb()
        it.drawRoundRect(
            left = leftPixel,
            top = topPixel,
            right = rightPixel,
            bottom = bottomPixel,
            radiusX = borderRadius.toPx(),
            radiusY = borderRadius.toPx(),
            paint
        )
    }
}

/**
 * 圓形陰影
 *
 * @property color 陰影顏色
 * @property size 物件大小
 * @property blurRadius 欲在物件外圍再增添多大的陰影範圍
 * @property centerOffsetX 陰影對於物件中心點的偏移量
 * @property centerOffsetY 陰影對於物件中心點的偏移量
 */
fun Modifier.circleShadow(
    color: Color = Color.Black,
    size: Dp = 32.dp,
    blurRadius: Dp = 3.dp,
    centerOffsetX: Dp = 0.dp,
    centerOffsetY: Dp = 0.dp
) = drawBehind {
    this.drawIntoCanvas {
        val center = size.toPx() / 2
        val offsetX = center + centerOffsetX.toPx()
        val offsetY = center + centerOffsetY.toPx()
        val realRadius = center + blurRadius.toPx()

        val paint = Paint().apply {
            shader = RadialGradientShader(
                center = Offset(offsetX, offsetY),
                colors = listOf(color, Color.Transparent),
                radius = realRadius
            )
        }

        it.drawCircle(
            center = Offset(offsetX, offsetY),
            radius = realRadius,
            paint = paint
        )
    }
}


data class ShadowConfig(
    val shadowColor: Color = color_shadow,
    val offsetX: Dp = 3.dp,
    val offsetY: Dp = 5.dp,
    val blurRadius: Dp = 3.dp,
    val corner: Dp = 6.dp
)

fun Modifier.pocketShadow(
    config: ShadowConfig = ShadowConfig()
) = drawBehind {
    this.drawIntoCanvas { canvas ->
        val shadowPaint = Paint().apply {
            this.color = config.shadowColor
//            shader = LinearGradientShader(
//                from = Offset(
//                    width - 3f,
//                    height + 5f,
//                ),
//                to = Offset(
//                    width + 3f,
//                    height + 5f,
//                ),
//                colors = listOf(shadowColor, Color.Transparent)
//            )
        }

        if (config.blurRadius != 0.dp) {
            shadowPaint.asFrameworkPaint().apply {
                maskFilter = BlurMaskFilter(config.blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL)
                color = config.shadowColor.toArgb()
            }
        }

        val shadowBounds = size.toRect()

        canvas.drawRoundRect(
            left = shadowBounds.left,
            top = shadowBounds.top,
            right = shadowBounds.right + config.offsetX.toPx(),
            bottom = shadowBounds.bottom + config.offsetY.toPx(),
            radiusX = config.corner.toPx(),
            radiusY = config.corner.toPx(),
            paint = shadowPaint
        )
    }
}

/**
 * 口袋 aspectRatio 選擇性的 build modifier
 *
 * @param ratio 比例
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.pocketAspectRatio(ratio: Float? = null) = composed(
    factory = {
        ratio?.let { mRatio ->
            this.then(
                Modifier.aspectRatio(ratio = mRatio)
            )
        } ?: run {
            this
        }
    }
)

/**
 * 可以在 preview 顯示 padding 間距的小工具
 * 數值正規化 ➔ 直接使用figma上面的大小即可
 * 取值順序 : all ➔ 水平垂直 ➔ 各自方位
 *
 * @param all 統一全部
 * @param hv vertical and horizontal(水平, 垂直)
 * @param start 左
 * @param top 上
 * @param end 右
 * @param bottom 下
 * @param needDebug 是否關閉preview狀態的小工具
 */
@SuppressLint("UnnecessaryComposedModifier")
fun Modifier.pocketPadding(
    all: Int? = null,
    hv: Pair<Int?, Int?>? = null,
    start: Int? = null,
    top: Int? = null,
    end: Int? = null,
    bottom: Int? = null
) = composed(
    factory = {
        val leftPadding = all ?: hv?.first ?: start ?: 0
        val topPadding = all ?: hv?.second ?: top ?: 0
        val rightPadding = all ?: hv?.first ?: end ?: 0
        val bottomPadding = all ?: hv?.second ?: bottom ?: 0

        if (isPreviewMode() && LocalSpacerIndicator.current) {
            this.drawPaddingHint(
                start = leftPadding,
                top = topPadding,
                end = rightPadding,
                bottom = bottomPadding
            )
        } else {
            this.padding(
                start = leftPadding.sdp(),
                top = topPadding.sdp(),
                end = rightPadding.sdp(),
                bottom = bottomPadding.sdp()
            )
        }
    }
)

/**
 * 填入欲顯示方位的數值
 *
 * @param start 左
 * @param top 上
 * @param end 又
 * @param bottom 下
 */
@SuppressLint("UnnecessaryComposedModifier")
private fun Modifier.drawPaddingHint(
    start: Int? = null,
    top: Int? = null,
    end: Int? = null,
    bottom: Int? = null
) = composed(
    factory = {
        val primaryColor = MaterialTheme.colorScheme.primary
        val onPrimaryColor = MaterialTheme.colorScheme.onPrimary

        val numberStartDp = start?.sdp() ?: 0.dp
        val numberStartText = start.toNumberFormat(invalidText = "--")
        val numberStartTextSize = numberStartDp / 3 * 2

        val numberTopDp = top?.sdp() ?: 0.dp
        val numberTopText = top.toNumberFormat(invalidText = "--")
        val numberTopTextSize = numberTopDp / 3 * 2

        val numberEndDp = end?.sdp() ?: 0.dp
        val numberEndText = end.toNumberFormat(invalidText = "--")
        val numberEndTextSize = numberEndDp / 3 * 2

        val numberBottomDp = bottom?.sdp() ?: 0.dp
        val numberBottomText = bottom.toNumberFormat(invalidText = "--")
        val numberBottomTextSize = numberBottomDp / 3 * 2

        this
            .drawWithContent {
                val paddingStartPx = numberStartDp.toPx()
                val paddingTopPx = numberTopDp.toPx()
                val paddingEndPx = numberEndDp.toPx()
                val paddingBottomPx = numberBottomDp.toPx()

                start?.let {
                    drawCircleNumber(
                        text = numberStartText,
                        drawTextSize = numberStartTextSize,
                        primaryColor = primaryColor,
                        onPrimaryColor = onPrimaryColor,
                        radius = paddingStartPx / 2,
                        direction = DrawDirection.LEFT
                    )
                }

                top?.let {
                    drawCircleNumber(
                        text = numberTopText,
                        drawTextSize = numberTopTextSize,
                        primaryColor = primaryColor,
                        onPrimaryColor = onPrimaryColor,
                        radius = paddingTopPx / 2,
                        direction = DrawDirection.TOP
                    )
                }

                end?.let {
                    drawCircleNumber(
                        text = numberEndText,
                        drawTextSize = numberEndTextSize,
                        primaryColor = primaryColor,
                        onPrimaryColor = onPrimaryColor,
                        radius = paddingEndPx / 2,
                        direction = DrawDirection.RIGHT
                    )
                }

                bottom?.let {
                    drawCircleNumber(
                        text = numberBottomText,
                        drawTextSize = numberBottomTextSize,
                        primaryColor = primaryColor,
                        onPrimaryColor = onPrimaryColor,
                        radius = paddingBottomPx / 2,
                        direction = DrawDirection.BOTTOM
                    )
                }

                drawContent()
            }
            .padding(
                start = numberStartDp,
                top = numberTopDp,
                end = numberEndDp,
                bottom = numberBottomDp
            )
    }
)