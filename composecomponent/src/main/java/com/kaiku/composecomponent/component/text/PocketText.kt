package com.kaiku.composecomponent.component.text

import androidx.annotation.ColorRes
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.kaiku.composecomponent.component.click.SurfaceWithClickableEffect
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.rememberClickableConfig
import com.kaiku.composecomponent.utils.text14Sp

/**
 * 口袋文字設定
 *
 * @property value 文字內容
 * @property valueAnnotated annotated 文字內容
 * @property textColor 文字顏色
 * @property disableTextColor disable狀態下的文字顏色
 * @property style 文字樣式(粗體大小)
 * @property isEnable 是否為可點擊狀態
 * @property alignment 外部對齊方式
 * @property textAlign 內部對齊方式
 * @property letterSpacing 文字間隔
 * @property overflow 文字溢出樣式(預設超出為...)
 * @property maxLines 最大文字行數(預設一行)
 */
data class PocketTextConfig(
    val value: String = "",
    val valueAnnotated: AnnotatedString? = null,
    @ColorRes val textColor: Color = Color.White,
    @ColorRes val disableTextColor: Color = Color.Unspecified,
    val style: TextStyle = TextStyle.Default,
    val isEnable: Boolean = true,
    val alignment: Alignment = Alignment.Center,
    val textAlign: TextAlign = TextAlign.Unspecified,
    val letterSpacing: TextUnit = TextUnit.Unspecified,
    val lineHeight: TextUnit = TextUnit.Unspecified,
    val overflow: TextOverflow = TextOverflow.Ellipsis,
    val softWrap: Boolean = true,
    val maxLines: Int = Int.MAX_VALUE,
    val onTextLayout: (TextLayoutResult) -> Unit = {}
)

/**
 * 用Box包裝Text來達成佈局上面的便捷性
 *
 * @param config BaseTextConfig
 */
@Composable
fun PocketText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    config: PocketTextConfig = PocketTextConfig()
) {
    Box(
        modifier = modifier,
        contentAlignment = config.alignment
    ) {
        if (config.valueAnnotated != null) {
            Text(
                text = config.valueAnnotated,
                style = config.style,
                maxLines = config.maxLines,
                letterSpacing = config.letterSpacing,
                lineHeight = config.lineHeight,
                textAlign = config.textAlign,
                softWrap = config.softWrap,
                onTextLayout = config.onTextLayout
            )
        } else {
            Text(
                modifier = textModifier,
                text = config.value,
                style = config.style,
                color = if (config.isEnable) {
                    config.textColor
                } else {
                    if(config.disableTextColor == Color.Unspecified) {
                        config.textColor
                    } else {
                        config.disableTextColor
                    }
                },
                maxLines = config.maxLines,
                overflow = config.overflow,
                letterSpacing = config.letterSpacing,
                lineHeight = config.lineHeight,
                textAlign = config.textAlign,
                softWrap = config.softWrap,
                onTextLayout = config.onTextLayout
            )
        }
    }
}

/**
 * 簡單的文字元件 預設字體大小為 14sp FontWeight為 400
 *
 * @param text 文字內容
 */
@Composable
fun SimpleText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
) {
    PocketText(
        modifier = modifier,
        textModifier = textModifier,
        config = PocketTextConfig(
            value = text,
            style = text14Sp()
        )
    )
}

/**
 * 帶有動畫效果的 SimpleText
 *
 * @param config PocketTextConfig設定
 * @param colorKey 文案顏色動畫觸發條件(預設為當文案改變時觸發)
 * @param aniSpec 文案顏色動畫轉換設定
 * @param transitionSpec 文案區域動畫轉換設定
 * @param defaultColor 初始顏色state(預設為白色)
 */
@Composable
fun AnimatedPocketText(
    modifier: Modifier = Modifier,
    aniModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    config: PocketTextConfig = PocketTextConfig(),
    colorKey: Any = config.value,
    aniSpec: AnimationSpec<Color>? = null,
    transitionSpec: AnimatedContentTransitionScope<String>.() -> ContentTransform = {
        fadeIn(animationSpec = tween(durationMillis = 500)) togetherWith
                fadeOut(animationSpec = tween(durationMillis = 500))
    },
    defaultColor: Color = Color.White
) {
    // 文字 color 改變動畫目前先這樣比較不突兀
    val aniColor = remember { Animatable(defaultColor) }

    LaunchedEffect(colorKey) {
        if (aniColor.value.value != config.textColor.value) {
            // 只有當顏色不一樣時才會animate
            aniColor.animateTo(
                targetValue = config.textColor,
                animationSpec = aniSpec ?: spring()
            )
        }
    }

    AnimatedContent(
        modifier = aniModifier,
        targetState = config.value,
        transitionSpec = transitionSpec,
        contentAlignment = Alignment.Center,
        label = ""
    ) { targetCount ->
        PocketText(
            modifier = modifier,
            textModifier = textModifier,
            config = config.copy(
                value = targetCount,
                textColor = aniColor.value
            )
        )
    }
}

/**
 * 帶有 AnnotatedString 的文字元件
 *
 * @param text 文字內容
 */
@Composable
fun PocketAnnotatedText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign = TextAlign.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = style,
            textAlign = textAlign,
            letterSpacing = letterSpacing,
            lineHeight = lineHeight,
            overflow = overflow,
            maxLines = maxLines
        )
    }
}

/**
 * 擁有點擊事件的 SimpleText
 *
 * @param config PocketTextConfig
 * @param clickableConfig clickable設定
 * @param onClick export 點擊事件
 */
@Composable
fun PocketTextWithClickEffect(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    config: PocketTextConfig = PocketTextConfig(),
    clickableConfig: ClickableConfig = rememberClickableConfig(
        needSound = config.isEnable,
        needRipple = config.isEnable
    ),
    onClick: () -> Unit
) {
    PocketText(
        modifier = modifier.clickableEffectConfig(
            config = clickableConfig,
            onClick = {
                if (config.isEnable) {
                    onClick.invoke()
                }
            }
        ),
        textModifier = textModifier,
        config = config
    )
}

/**
 * 擁有點擊效果的 PocketText
 *
 * @param shape surface shape
 * @param color surface color
 * @param border surface border
 * @param config config
 * @param clickableConfig clickable設定
 * @param onClick export 點擊事件
 */
@Composable
fun PocketTextWithClickableEffect(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.surface,
    border: BorderStroke? = null,
    config: PocketTextConfig = PocketTextConfig(),
    clickableConfig: ClickableConfig = rememberClickableConfig(
        needSound = config.isEnable,
        needRipple = config.isEnable,
        clickEffect = false
    ),
    onClick: () -> Unit
) {
    SurfaceWithClickableEffect(
        modifier = modifier,
        shape = shape,
        color = color,
        border = border,
        isScaleEnabled = config.isEnable,
        clickableConfig = clickableConfig,
        content = {
            PocketText(
                modifier = contentModifier.align(Alignment.Center),
                textModifier = textModifier,
                config = config
            )
        },
        onClick = {
            if (config.isEnable) {
                onClick.invoke()
            }
        }
    )
}