package com.kaiku.composecomponent.extension

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp

enum class DrawDirection {
    LEFT,
    TOP,
    RIGHT,
    BOTTOM,
    CENTER
}

fun ContentDrawScope.drawCircleNumber(
    text: String,
    drawTextSize: Dp,
    primaryColor: Color,
    onPrimaryColor: Color,
    radius: Float,
    direction: DrawDirection = DrawDirection.BOTTOM
) {
    val (offsetX, offsetY) = when (direction) {
        DrawDirection.LEFT -> (radius to size.height / 2)
        DrawDirection.TOP -> (size.width / 2 to radius)
        DrawDirection.RIGHT -> (size.width - radius to size.height / 2)
        DrawDirection.BOTTOM -> (size.width / 2 to size.height - radius)
        DrawDirection.CENTER -> (size.width / 2 to size.height / 2)
    }

    drawCircle(
        color = primaryColor,
        radius = radius,
        center = Offset(offsetX, offsetY)
    )
    drawIntoCanvas { canvas ->
        val paint = android.graphics
            .Paint()
            .apply {
                color = onPrimaryColor.toArgb()
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = drawTextSize.toPx()
                isAntiAlias = true
                typeface = android.graphics.Typeface.create(
                    android.graphics.Typeface.DEFAULT,
                    android.graphics.Typeface.BOLD
                )
            }

        val x = offsetX
        val y = offsetY - (paint.descent() + paint.ascent()) / 2

        canvas.nativeCanvas.drawText(
            text,
            x,
            y,
            paint
        )
    }
}