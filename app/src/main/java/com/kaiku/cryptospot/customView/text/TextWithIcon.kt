package com.kaiku.cryptospot.customView.text


import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.cryptospot.R


@Composable
fun TextWithIcon(
    modifier: Modifier = Modifier,
    @DrawableRes drawableTop: Int? = null,
    topPaddingToText: Dp = 5.dp,
    @DrawableRes drawableStart: Int? = null,
    startPaddingToText: Dp = 5.dp,
    @DrawableRes drawableBottom: Int? = null,
    bottomPaddingToText: Dp = 5.dp,
    @DrawableRes drawableEnd: Int? = null,
    endPaddingToText: Dp = 5.dp,
    drawableSize: Dp = 17.dp,
    content: @Composable () -> Unit,
) {

    ConstraintLayout(modifier) {
        val (refImgStart, refImgTop, refImgBottom, refImgEnd, refContent) = createRefs()
        Box(
            Modifier.constrainAs(refContent) {
                top.linkTo(drawableTop?.let { refImgTop.bottom } ?: parent.top)
                bottom.linkTo(drawableBottom?.let { refImgBottom.top } ?: parent.bottom)
                start.linkTo(drawableStart?.let { refImgStart.end } ?: parent.start)
                end.linkTo(drawableEnd?.let { refImgEnd.start } ?: parent.end)
            }
        ) {
            content()
        }
        drawableTop?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier
                    .constrainAs(refImgTop) {
                        top.linkTo(parent.top)
                        start.linkTo(refContent.start)
                        end.linkTo(refContent.end)
                    }
                    .padding(bottom = topPaddingToText)
                    .size(drawableSize)
            )
        }
        drawableStart?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier
                    .constrainAs(refImgStart) {
                        top.linkTo(refContent.top)
                        bottom.linkTo(refContent.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(end = startPaddingToText)
                    .size(drawableSize)
            )
        }
        drawableBottom?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier
                    .constrainAs(refImgBottom) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(refContent.start)
                        end.linkTo(refContent.end)
                    }
                    .padding(top = bottomPaddingToText)
                    .size(drawableSize)
            )
        }
        drawableEnd?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier
                    .constrainAs(refImgEnd) {
                        top.linkTo(refContent.top)
                        bottom.linkTo(refContent.bottom)
                        end.linkTo(parent.end)
                    }
                    .padding(start = endPaddingToText)
                    .size(drawableSize)
            )
        }
    }
}

@Preview
@Composable
private fun TextViewIconPreview() {
    TextWithIcon(
        drawableStart = R.drawable.ic_launcher_foreground,
        drawableEnd = R.drawable.ic_launcher_foreground,
        drawableBottom = R.drawable.ic_launcher_foreground,
        drawableTop = R.drawable.ic_launcher_foreground
    ) {
        Text(
            text = "title",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}

