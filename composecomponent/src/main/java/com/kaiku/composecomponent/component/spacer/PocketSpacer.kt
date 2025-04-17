package com.kaiku.composecomponent.component.spacer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.kaiku.composecomponent.LocalProvider.LocalSpacerIndicator
import com.kaiku.composecomponent.component.text.SimpleText
import com.kaiku.composecomponent.extension.toNumberFormat
import com.kaiku.composecomponent.utils.isPreviewMode
import com.kaiku.composecomponent.utils.sdp


@Composable
fun BasePocketSpacer(
    modifier : Modifier = Modifier,
    debugModifier: Modifier = Modifier,
    needDebug: Boolean = true,
    figmaWidth: Int? = null,
    figmaHeight: Int? = null
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val onPrimaryColor = MaterialTheme.colorScheme.onPrimary
    val number = figmaWidth ?: figmaHeight ?: 0
    val numberDp = number.sdp()
    val numberText = number.toNumberFormat(invalidText = "--")
    val mTextSize = numberDp / 3 * 2

    if (isPreviewMode() && needDebug) {
        Box(
            modifier = debugModifier,
            contentAlignment = Alignment.Center
        ) {
            Spacer(
                modifier = modifier
                    .size(numberDp)
                    .drawWithContent {
                        drawCircle(
                            color = primaryColor,
                            radius = size.minDimension / 2
                        )

                        // Draw the number inside the circle
                        drawIntoCanvas { canvas ->
                            val paint = android.graphics
                                .Paint()
                                .apply {
                                    color = onPrimaryColor.toArgb()
                                    textAlign = android.graphics.Paint.Align.CENTER
                                    textSize = mTextSize.toPx()
                                    isAntiAlias = true
                                    typeface = android.graphics.Typeface.create(
                                        android.graphics.Typeface.DEFAULT,
                                        android.graphics.Typeface.BOLD
                                    )
                                }

                            // Draw the text at the center of the circle
                            val x = size.width / 2
                            val y = size.height / 2 - (paint.descent() + paint.ascent()) / 2

                            canvas.nativeCanvas.drawText(
                                numberText,
                                x,
                                y,
                                paint
                            )
                        }
                    }
            )
        }
    } else {
        figmaWidth?.let {
            Spacer(modifier = Modifier.width(it.sdp()))
        } ?: run {
            figmaHeight?.let {
                Spacer(modifier = Modifier.height(it.sdp()))
            }
        } ?: Spacer(modifier = Modifier)
    }
}

@Composable
fun ColumnScope.PocketSpacer(
    modifier: Modifier = Modifier,
    needDebug: Boolean = LocalSpacerIndicator.current,
    height: Int
) {
    BasePocketSpacer(
        modifier = modifier,
        debugModifier = Modifier.align(Alignment.CenterHorizontally),
        needDebug = needDebug,
        figmaHeight = height
    )
}

@Composable
fun RowScope.PocketSpacer(
    modifier: Modifier = Modifier,
    needDebug: Boolean = LocalSpacerIndicator.current,
    width: Int
) {
    BasePocketSpacer(
        modifier = modifier,
        debugModifier = Modifier.align(Alignment.CenterVertically),
        needDebug = needDebug,
        figmaWidth = width
    )
}

@Preview
@Composable
private fun PocketSpacerPreview() {
    Column {
        SimpleText(text = "第一行")
        PocketSpacer(height = 10)
        SimpleText(text = "第二行")
    }
}
