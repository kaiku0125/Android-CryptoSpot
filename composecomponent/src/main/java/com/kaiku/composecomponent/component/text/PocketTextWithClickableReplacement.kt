package com.kaiku.composecomponent.component.text

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.kaiku.composecomponent.extension.splitByReplacements

@Composable
fun PocketTextWithClickableReplacement(
    modifier: Modifier = Modifier,
    config: PocketTextConfig,
    replaceConfigs: List<PocketTextConfig>,
    onReplaceClick: (Int, String) -> Unit
) {
    if(replaceConfigs.isEmpty()) {
        PocketText(
            modifier = modifier,
            config = config
        )
    } else {
        val separate = config.value.splitByReplacements(
            replaceConfigs.map { it.value }
        )

        val text = buildAnnotatedString {
            separate.forEach { separateString ->
                val foundConfig = replaceConfigs.find { it.value == separateString }
                val index = replaceConfigs.indexOfFirst { it.value == separateString }

                if (foundConfig != null) {
                    pushStringAnnotation(tag = "TAG_$index", annotation = foundConfig.value)
                    withStyle(
                        style = SpanStyle(
                            fontSize = foundConfig.style.fontSize,
                            fontWeight = foundConfig.style.fontWeight,
                            color = foundConfig.textColor,
                            textDecoration = TextDecoration.Underline
                        )
                    ) {
                        append(separateString)
                    }
                    pop()  // 標記結束
                } else {
                    withStyle(
                        style = SpanStyle(
                            fontSize = config.style.fontSize,
                            fontWeight = config.style.fontWeight,
                            color = config.textColor
                        )
                    ) {
                        append(separateString)
                    }
                }
            }
        }

        Box(
            modifier = modifier,
            contentAlignment = config.alignment
        ) {
            val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
            val pressIndicator = Modifier.pointerInput(onReplaceClick) {
                detectTapGestures { pos ->
                    layoutResult.value?.let { layoutResult ->
                        val offset = layoutResult.getOffsetForPosition(pos)
                        text.getStringAnnotations(start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                val index = replaceConfigs.indexOfFirst { it.value == annotation.item }
                                onReplaceClick.invoke(
                                    index, annotation.item
                                )
                            }
                    }
                }
            }

            BasicText(
                text = text,
                modifier = modifier.then(pressIndicator),
                style = TextStyle(textAlign = config.textAlign),
                onTextLayout = {
                    layoutResult.value = it
                }
            )
        }
    }
}