package com.kaiku.composecomponent.component.text

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.lifecycle.Lifecycle
import com.kaiku.composecomponent.color_292929
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.rememberClickableConfig
import com.kaiku.composecomponent.utils.OnComposeLifecycleEvent
import com.kaiku.composecomponent.utils.basic.getScreenPx
import com.kaiku.composecomponent.utils.sdp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

/**
 *
 * @property text 一般文字 (與annotatedText擇一即可)
 * @property annotatedText annotate文字 (與text擇一即可)
 * @property isLinkable 是否可被點擊
 */
data class MarqueeTextConfig(
    val text: List<String> = emptyList(),
    val annotatedText: List<AnnotatedString> = emptyList(),
    val isLinkable: List<Boolean> = emptyList()
)

/**
 * MarqueeText 口袋文字跑馬燈
 *
 * @description 跑馬燈文字目前動畫會根據文本寬度進行animation，所以如果文本寬度改變會導致跑馬燈抖動
 *              因此目前的解法為，先將文本固定，之後再考慮更佳的解法
 * @param config 跑馬燈文案設定檔
 * @param gradientEdgeColor 跑馬燈左右漸層(通常輸入背景顏色)
 * // -- 省略其他基本的Text參數 -- //
 * @param onResumeDelay 根據生命週期動畫延遲播放
 * @param defaultDuration 動畫播放速度
 * @param initPosition 初始文案偏移量
 * @param hardCodeWidth 文本寬度 (null 為自適應大小)
 * @param spacing 循環文本夾間隔
 * @param textSpacing text與text之間的間隔
 * @param onClick export文本點擊事件
 */
@Composable
fun MarqueeText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    config: MarqueeTextConfig = MarqueeTextConfig(),
    gradientEdgeColor: Color = color_292929,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    onResumeDelay: Long = 0,
    defaultDuration: Int = 7500,
    initPosition: Int = getScreenPx().toInt(),
    hardCodeWidth: Int? = null,
    spacing: Int? = null,
    textSpacing: Int = 16,
    onClick: ((Int) -> Unit)? = null
) {
    val scope = rememberCoroutineScope()
    var lifecycleJob by remember { mutableStateOf<Job?>(null) }
    val screenPx = getScreenPx()
    val screenWidth by remember { mutableIntStateOf(screenPx.toInt()) }

    // 根據生命週期來啟動跑馬燈動畫
    var isUIEnable by remember { mutableStateOf(false) }

    // 強制讓跑馬燈動畫初始化的參數
    var resetKey by remember { mutableLongStateOf(System.currentTimeMillis()) }

    // 跑馬燈是否已消耗初始偏移量
    var hasInitPositionConsumed by remember { mutableStateOf(false) }

    // 動畫的起始位置，會根據 hasInitPositionConsumed 來衍生
    val aniStartPosition = remember {
        derivedStateOf {
            if (hasInitPositionConsumed) {
                0
            } else {
                initPosition
            }
        }
    }.value

    OnComposeLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                resetKey = System.currentTimeMillis()
                lifecycleJob?.cancel()
                lifecycleJob = scope.launch {
                    delay(onResumeDelay)
                    ensureActive()
                    isUIEnable = true
                }
            }

            Lifecycle.Event.ON_PAUSE -> {
                lifecycleJob?.cancel()
                lifecycleJob = scope.launch {
                    // TODO: 為了維持點擊時text不會因為生命週期而亂抖動，目前先用延遲解決
                    delay(500L)
                    ensureActive()
                    isUIEnable = false
                    hasInitPositionConsumed = false
                }
            }

            else -> Unit
        }
    }

    val createMarqueeEffect = @Composable { localModifier: Modifier ->
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (config.annotatedText.isNotEmpty()) {
                config.annotatedText.forEachIndexed { index, annoText ->
                    val isLinkable = config.isLinkable.getOrElse(index) { false }

                    Text(
                        text = annoText,
                        textAlign = textAlign,
                        modifier = localModifier.clickableEffectConfig(
                            config = rememberClickableConfig(
                                needRipple = false,
                                needHaptic = isLinkable,
                                clickEffect = isLinkable
                            ),
                            onClick = {
                                if (isLinkable) {
                                    onClick?.invoke(index)
                                }
                            }
                        ),
                        color = color,
                        fontSize = fontSize,
                        fontStyle = fontStyle,
                        fontWeight = fontWeight,
                        fontFamily = fontFamily,
                        letterSpacing = letterSpacing,
                        textDecoration = textDecoration,
                        lineHeight = lineHeight,
                        overflow = overflow,
                        softWrap = softWrap,
                        maxLines = 1,
                        onTextLayout = onTextLayout,
                        style = style,
                    )
                    if (index != config.annotatedText.lastIndex) {
                        PocketSpacer(width = textSpacing)
                    }
                }
            } else if (config.text.isNotEmpty()) {
                config.text.forEachIndexed { index, mText ->
                    val isLinkable = config.isLinkable.getOrElse(index) { false }

                    Text(
                        text = mText,
                        textAlign = textAlign,
                        modifier = localModifier.clickableEffectConfig(
                            config = rememberClickableConfig(
                                needRipple = false,
                                needHaptic = isLinkable,
                                clickEffect = isLinkable
                            ),
                            onClick = {
                                if (isLinkable) {
                                    onClick?.invoke(index)
                                }
                            }
                        ),
                        color = color,
                        fontSize = fontSize,
                        fontStyle = fontStyle,
                        fontWeight = fontWeight,
                        fontFamily = fontFamily,
                        letterSpacing = letterSpacing,
                        textDecoration = textDecoration,
                        lineHeight = lineHeight,
                        overflow = overflow,
                        softWrap = softWrap,
                        maxLines = 1,
                        onTextLayout = onTextLayout,
                        style = style,
                    )
                    if (index != config.text.lastIndex) {
                        PocketSpacer(width = textSpacing)
                    }
                }
            } else {
                Box(modifier = modifier)
            }
        }
    }

    if (config.text.isNotEmpty() || config.annotatedText.isNotEmpty()) {
        var offset by remember { mutableIntStateOf(0) }
        val textLayoutInfoState = remember { mutableStateOf<TextLayoutInfo?>(null) }

        //跑馬燈動畫reset時機
        LaunchedEffect(resetKey, isUIEnable) {
            if (isUIEnable.not()) {
                offset = initPosition
            }
        }

        // 跑馬燈動畫邏輯
        LaunchedEffect(textLayoutInfoState.value, isUIEnable, aniStartPosition) {
            if (isUIEnable.not()) return@LaunchedEffect
            val textLayoutInfo = textLayoutInfoState.value ?: return@LaunchedEffect
            if (textLayoutInfo.textWidth <= textLayoutInfo.containerWidth) return@LaunchedEffect

            // 一開始的總偏移量為 字串寬度+起始偏移，之後都是從依照字串寬度開始跑
            val duration = if (hasInitPositionConsumed) {
                textLayoutInfo.textWidth
            } else {
                textLayoutInfo.textWidth + initPosition
            }.times(defaultDuration).div(textLayoutInfo.containerWidth)

            do {
                // 定義動畫，文字偏移量從 0 ~ 文本寬度
                val animation = TargetBasedAnimation(
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = duration,
                            delayMillis = 0,
                            easing = LinearEasing,
                        ),
                        repeatMode = RepeatMode.Restart
                    ),
                    typeConverter = Int.VectorConverter,
                    initialValue = aniStartPosition,
                    targetValue = -textLayoutInfo.textWidth
                )
                // 根據動畫時間獲取偏移量
                // 起始時間
                val startTime = withFrameNanos { it }
                do {
                    val playTime = withFrameNanos { it } - startTime
                    offset = (animation.getValueFromNanos(playTime))
                    // 當偏移量歸0，表示初始偏移量已消耗完畢，之後就走正常流程
                    if (offset < 0 && hasInitPositionConsumed.not()) {
                        hasInitPositionConsumed = true
                    }
                } while (!animation.isFinishedFromNanos(playTime))
                // 延遲重新播放
                delay(1000L)
            } while (true)
        }

        SubcomposeLayout(
            modifier = modifier.clipToBounds()
        ) { constraints ->
            // 測量文本寬度
            val infiniteWidthConstraints = constraints.copy(maxWidth = Int.MAX_VALUE)
            var mainText = subcompose(MarqueeLayers.MainText) {
                createMarqueeEffect(textModifier)
            }.first().measure(infiniteWidthConstraints)

            // 定義最大寬度(須為固定)
            val localHardCodedWidth = hardCodeWidth ?: run {
                if (mainText.width < screenWidth) {
                    // 若字串長度不夠，則讓它與螢幕同寬
                    screenWidth
                } else {
                    mainText.width
                }
            }

            var gradient: Placeable? = null

            var secondPlaceableWithOffset: Pair<Placeable, Int>? = null
            if (localHardCodedWidth <= constraints.maxWidth) {
                mainText = subcompose(MarqueeLayers.SecondaryText) {
                    createMarqueeEffect(textModifier.fillMaxWidth())
                }.first().measure(constraints)
                textLayoutInfoState.value = null
            } else {
                // 循環文本夾間隔
                val mSpacing = spacing ?: (constraints.maxWidth / 3)

                textLayoutInfoState.value = TextLayoutInfo(
                    textWidth = localHardCodedWidth + mSpacing,
                    containerWidth = constraints.maxWidth
                )
                // 第二遍文本偏移量
                val secondTextOffset = localHardCodedWidth + offset + mSpacing
                val secondTextSpace = constraints.maxWidth - secondTextOffset
                if (secondTextSpace > 0) {
                    secondPlaceableWithOffset = subcompose(MarqueeLayers.SecondaryText) {
                        createMarqueeEffect(textModifier)
                    }.first().measure(infiniteWidthConstraints) to secondTextOffset
                }
                // 測量左右兩邊gradient元件
                gradient = subcompose(MarqueeLayers.EdgesGradient) {
                    Row {
                        GradientEdge(gradientEdgeColor, Color.Transparent)
                        Spacer(Modifier.weight(1f))
                        GradientEdge(Color.Transparent, gradientEdgeColor)
                    }
                }.first().measure(constraints.copy(maxHeight = mainText.height))
            }

            // 佈局文本
            layout(
                width = constraints.maxWidth,
                height = mainText.height
            ) {
                mainText.place(offset, 0)
                secondPlaceableWithOffset?.let {
                    it.first.place(it.second, 0)
                }
                gradient?.place(0, 0)
            }
        }
    } else {
        createMarqueeEffect.invoke(modifier)
    }
}

@Composable
private fun GradientEdge(
    startColor: Color, endColor: Color,
) {
    Box(
        modifier = Modifier
            .width(10.sdp())
            .fillMaxHeight()
            .background(
                brush = Brush.horizontalGradient(
                    0f to startColor, 1f to endColor,
                )
            )
    )
}

private enum class MarqueeLayers { MainText, SecondaryText, EdgesGradient }
private data class TextLayoutInfo(val textWidth: Int, val containerWidth: Int)