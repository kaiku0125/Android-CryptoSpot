package com.kaiku.composecomponent.component.text

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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontFamilyResolver
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isFinite
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.isUnspecified
import androidx.compose.ui.unit.sp
import com.kaiku.composecomponent.utils.isPreviewMode
import timber.log.Timber
import kotlin.math.absoluteValue

/**
 * 大神寫的自適應文案
 *
 * 主要設定參數為一下
 * @param text 文案
 * @param fontWeight 字體粗體
 * @param color 文案顏色
 * @param textAlign 對其方式
 * @param contentAlignment 對其方式
 * @param maxFontSize 最大字體大小(直接填入figma的大小)
 * @param maxLines 文案換行最大行數
 * @param isAnimated 是否需要文案轉換動畫
 */
@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    acceptableError: Dp = 5.dp,
    maxFontSize: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    contentAlignment: Alignment? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    isAnimated: Boolean = false
) {
    val alignment: Alignment = contentAlignment ?: when (textAlign) {
        TextAlign.Left -> Alignment.TopStart
        TextAlign.Right -> Alignment.TopEnd
        TextAlign.Center -> Alignment.Center
        TextAlign.Justify -> Alignment.TopCenter
        TextAlign.Start -> Alignment.TopStart
        TextAlign.End -> Alignment.TopEnd
        else -> Alignment.TopStart
    }
    BoxWithConstraints(modifier = modifier, contentAlignment = alignment) {
        var shrunkFontSize = if (maxFontSize.isSpecified) maxFontSize else 100.sp

        val calculateIntrinsics = @Composable {
            val mergedStyle = style.merge(
                TextStyle(
                    color = color,
                    fontSize = shrunkFontSize,
                    fontWeight = fontWeight,
                    textAlign = textAlign ?: TextAlign.Unspecified,
                    lineHeight = lineHeight,
                    fontFamily = fontFamily,
                    textDecoration = textDecoration,
                    fontStyle = fontStyle,
                    letterSpacing = letterSpacing
                )
            )
            Paragraph(
                text = text.ifEmpty { " " },
                style = mergedStyle,
                constraints = Constraints(maxWidth = kotlin.math.ceil(LocalDensity.current.run { maxWidth.toPx() }
                    .toDouble()).toInt()),
                density = LocalDensity.current,
                fontFamilyResolver = LocalFontFamilyResolver.current,
                spanStyles = listOf(),
                placeholders = listOf(),
                maxLines = maxLines,
                ellipsis = false
            )
        }

        var intrinsics = calculateIntrinsics()

        val targetWidth = maxWidth - acceptableError / 2f

        check(targetWidth.isFinite || maxFontSize.isSpecified) { "maxFontSize must be specified if the target with isn't finite!" }

        with(LocalDensity.current) {
            // this loop will attempt to quickly find the correct size font by scaling it by the error
            // it only runs if the max font size isn't specified or the font must be smaller
            // minIntrinsicWidth is "The width for text if all soft wrap opportunities were taken."
            if (maxFontSize.isUnspecified || targetWidth < intrinsics.minIntrinsicWidth.toDp())
                while ((targetWidth - intrinsics.minIntrinsicWidth.toDp()).toPx().absoluteValue.toDp() > acceptableError / 2f) {
                    shrunkFontSize *= targetWidth.toPx() / intrinsics.minIntrinsicWidth
                    intrinsics = calculateIntrinsics()
                }
            // checks if the text fits in the bounds and scales it by 90% until it does
            while (intrinsics.didExceedMaxLines || maxHeight < intrinsics.height.toDp() || maxWidth < intrinsics.minIntrinsicWidth.toDp()) {
                shrunkFontSize *= 0.9f
                intrinsics = calculateIntrinsics()
            }
        }

        if (maxFontSize.isSpecified && shrunkFontSize > maxFontSize)
            shrunkFontSize = maxFontSize

        if (isAnimated) {
            AnimatedPocketText(
                config = PocketTextConfig(
                    value = text,
                    textColor = color,
                    style = TextStyle.Default.copy(
                        fontSize = shrunkFontSize,
                        fontWeight = fontWeight,
                        fontFamily = fontFamily,
                        fontStyle = fontStyle
                    ),
                    letterSpacing = letterSpacing,
                    textAlign = textAlign ?: TextAlign.Unspecified,
                    overflow = TextOverflow.Clip,
                    lineHeight = lineHeight,
                    maxLines = maxLines
                )
            )
        } else {
            Text(
                text = text,
                color = color,
                fontSize = shrunkFontSize,
                fontStyle = fontStyle,
                fontWeight = fontWeight,
                fontFamily = fontFamily,
                letterSpacing = letterSpacing,
                textDecoration = textDecoration,
                textAlign = textAlign,
                lineHeight = lineHeight,
                onTextLayout = onTextLayout,
                maxLines = maxLines,
                style = style
            )
        }
    }
}

/**
 * PocketAutoSizeText 用在list相關的情境時，可能會讓編譯無法測量維度，因此無法使用，
 * 建議使用 [AnimatedPocketAutoSizeText]
 */
@Composable
fun PocketAutoSizeText(
    modifier: Modifier = Modifier,
    config: PocketTextConfig,
    isAnimated: Boolean = true
) {
    AutoSizeText(
        text = config.value,
        modifier = modifier,
        maxFontSize = config.style.fontSize,
        color = config.textColor,
        fontStyle = config.style.fontStyle,
        fontWeight = config.style.fontWeight,
        fontFamily = config.style.fontFamily,
        letterSpacing = config.letterSpacing,
        textAlign = config.textAlign,
        contentAlignment = config.alignment,
        lineHeight = config.lineHeight,
        maxLines = config.maxLines,
        style = config.style,
        isAnimated = isAnimated
    )
}


/**
 * AnimatedPocketAutoSizeText
 *
 * @param config PocketTextConfig設定
 * @param colorKey 文案顏色動畫觸發條件(預設為當文案改變時觸發)
 * @param aniSpec 文案顏色動畫轉換設定
 * @param transitionSpec 文案區域動畫轉換設定
 * @param defaultColor 初始顏色state(預設為白色)
 * @param needFineTune 是否需要resize後的padding微調
 * @param tag 提供debug元件用的tag (Debug完記得移除)
 */
@Composable
fun AnimatedPocketAutoSizeText(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    config: PocketTextConfig,
    colorKey: Any = config.value,
    aniSpec: AnimationSpec<Color>? = null,
    transitionSpec: AnimatedContentTransitionScope<String>.() -> ContentTransform = {
        fadeIn(animationSpec = tween(durationMillis = 500)) togetherWith
                fadeOut(animationSpec = tween(durationMillis = 500))
    },
    defaultColor: Color = Color.White,
    needFineTune: Boolean = true,
    tag: String = ""
) {
    val isPreview = isPreviewMode()
    val density = LocalDensity.current
    val mTag = remember {tag.ifEmpty { "unKnown" }}
    val inputSizeInt by remember{ mutableIntStateOf(config.style.fontSize.value.toInt()) }

    // 預設值為false，又為了讓preview可以看得到值，所以先這樣寫
    var readyToDraw by remember { mutableStateOf(isPreview) }

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
        modifier = modifier,
        targetState = config.value,
        transitionSpec = transitionSpec,
        contentAlignment = config.alignment,
        label = ""
    ) {

        val textStyle by remember { mutableStateOf(config.style) }
        var scaledTextStyle by remember { mutableStateOf(textStyle) }

        // 不確定為什麼resize之後無法對齊，目前先用這種方式微調
        val paddingFineTune = remember(scaledTextStyle) {
            val newSizeInt = scaledTextStyle.fontSize.value.toInt()
            derivedStateOf {
                if (needFineTune) {
                    with(density) {
                        (inputSizeInt - newSizeInt).sp.toPx()
                            .absoluteValue
                            .toDp().value.toInt()
                    }
                } else {
                    0
                }
            }
        }

        // Debug使用
        if (tag.isNotEmpty()) {
            LaunchedEffect(paddingFineTune) {
                Timber.tag(mTag).d("AnimatedPocketAutoSizeText paddingFineTune:${paddingFineTune.value}")
            }
        }

        Text(
            text = it,
            modifier = textModifier
                .padding(top = paddingFineTune.value.dp)
                .drawWithContent {
                    if (readyToDraw) {
                        drawContent()
                    }
                },
            color = aniColor.value,
            maxLines = 1,
            textAlign = config.textAlign,
            style = scaledTextStyle,
            softWrap = false,
            onTextLayout = { textLayoutResult ->
                if (textLayoutResult.didOverflowWidth) {
                    scaledTextStyle =
                        scaledTextStyle.copy(fontSize = scaledTextStyle.fontSize * 0.95)
                } else {
                    readyToDraw = true
                }
            }
        )
    }
}
