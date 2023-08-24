package com.kaiku.cryptospot.customView.text

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun AutoResizeText(
    text: String,
    fontSizeRange: FontSizeRange,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
    readyToDraw: Boolean = false,
    update: Boolean = false,
    hasDrawn: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var fontSizeValue by remember { mutableStateOf(fontSizeRange.max.value) }

    var draw by remember { mutableStateOf(readyToDraw) }

    LaunchedEffect(update){
        if(update){
            Timber.e("update : $update")
            fontSizeValue = fontSizeRange.max.value
        }
    }

    val calculateLambda = remember<(TextLayoutResult) -> Unit>
    {
        {
            scope.launch {
                if (it.didOverflowHeight && !readyToDraw) {
                    val nextFontSizeValue = fontSizeValue - fontSizeRange.step.value
                    if (nextFontSizeValue <= fontSizeRange.min.value) {
                        // Reached minimum, set minimum font size and it's readToDraw
                        fontSizeValue = fontSizeRange.min.value
                        draw = true
                        hasDrawn.invoke()
                    } else {
                        // Text doesn't fit yet and haven't reached minimum text range, keep decreasing
                        fontSizeValue = nextFontSizeValue
                    }
                } else {
                    // Text fits before reaching the minimum, it's readyToDraw
                    draw = true
                    hasDrawn.invoke()
                }
            }
        }
    }

    Text(
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                if (draw) {
                    drawContent()
                }
            },
        text = text,
        color = color,
        maxLines = maxLines,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        style = style,
        fontSize = fontSizeValue.sp,
        onTextLayout = {
            calculateLambda.invoke(it)
        },
    )

}

data class FontSizeRange(
    val min: TextUnit,
    val max: TextUnit,
    val step: TextUnit = DEFAULT_TEXT_STEP,
) {
    init {
        require(min < max) { "min should be less than max, $this" }
        require(step.value > 0) { "step should be greater than 0, $this" }
    }

    companion object {
        private val DEFAULT_TEXT_STEP = 1.sp
    }
}
