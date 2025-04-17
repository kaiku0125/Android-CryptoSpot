package com.kaiku.composecomponent.component.popup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.composecomponent.color_F0B51C
import com.kaiku.composecomponent.component.button.PocketPrimaryButton
import com.kaiku.composecomponent.component.shape.TriangleShape
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.clipByShape
import com.kaiku.composecomponent.extension.pocketPadding
import com.kaiku.composecomponent.utils.basic.getScreenWidth
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text16Sp
import timber.log.Timber

@Composable
fun ConversationBubblePopup(
    modifier: Modifier = Modifier,
    offset: IntOffset = IntOffset(0, 0),
    titleConfig: PocketTextConfig,
    contentConfig: PocketTextConfig,
    buttonConfig: PocketTextConfig = PocketTextConfig(
        value = "立即前往",
        style = text16Sp(700),
        textColor = Color.Black
    ),
    buttonBackground: Color = color_F0B51C,
    bubbleBackGround: Color = MaterialTheme.colorScheme.primary,
    onDismissRequest: (() -> Unit)? = null,
    onBtnClick: () -> Unit
) {

    Popup(
        offset = offset,
        onDismissRequest = onDismissRequest
    ) {
        ConstraintLayout(
            modifier = modifier.background(Color.Transparent)
        ) {
            val (triangle, infoBox) = createRefs()
            val m24sdp = 24.sdp()

            Box(
                modifier = Modifier
                    .width(16.sdp())
                    .height(8.sdp())
                    .clipByShape(
                        shape = TriangleShape(),
                        backgroundColor = bubbleBackGround
                    )
                    .constrainAs(triangle) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, margin = m24sdp)
                    }
            )

            Column(
                modifier = Modifier
                    .clipByShape(
                        shape = RoundedCornerShape(16.sdp()),
                        backgroundColor = bubbleBackGround
                    )
                    .pocketPadding(all = 16)
                    .constrainAs(infoBox) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(triangle.bottom)
                    },
            ) {
                PocketText(
                    modifier = Modifier,
                    config = titleConfig
                )
                PocketSpacer(height = 4)
                PocketText(
                    modifier = Modifier,
                    config = contentConfig
                )
                PocketSpacer(height = 10)
                PocketPrimaryButton(
                    modifier = Modifier.align(Alignment.End),
                    config = buttonConfig,
                    primaryColor = buttonBackground,
                    onClick = onBtnClick
                )
            }

        }
    }
}


@Composable
fun ConversationBubble(
    modifier: Modifier = Modifier,
    offset: IntOffset = IntOffset(0, 0),
    titleConfig: PocketTextConfig,
    contentConfig: PocketTextConfig,
    buttonConfig: PocketTextConfig = PocketTextConfig(
        value = "立即前往",
        style = text16Sp(700),
        textColor = Color.Black
    ),
    buttonBackground: Color = color_F0B51C,
    bubbleBackGround: Color = MaterialTheme.colorScheme.primary,
    onBtnClick: () -> Unit,
    onUICalculationError: () -> Unit
) {
    val density = LocalDensity.current
    val offsetX = with(density) { offset.x.toDp() }
    val offsetY = with(density) { offset.y.toDp() }

    val maxWidth = getScreenWidth() - offsetX
    if (maxWidth > 30.dp) {
        ConstraintLayout(
            modifier = modifier
                .widthIn(min = 10.sdp(), max = maxWidth)
                .background(Color.Transparent)
                .offset(
                    x = offsetX,
                    y = offsetY
                )
                .padding(end = 8.sdp())
        ) {
            val (triangle, infoBox) = createRefs()
            val m24sdp = 24.sdp()

            Box(
                modifier = Modifier
                    .width(16.sdp())
                    .height(8.sdp())
                    .clipByShape(
                        shape = TriangleShape(),
                        backgroundColor = bubbleBackGround
                    )
                    .constrainAs(triangle) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start, margin = m24sdp)
                    }
            )

            Column(
                modifier = Modifier
                    .clipByShape(
                        shape = RoundedCornerShape(16.sdp()),
                        backgroundColor = bubbleBackGround
                    )
                    .pocketPadding(all = 16)
                    .constrainAs(infoBox) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(triangle.bottom)
                    },
            ) {
                PocketText(
                    modifier = Modifier,
                    config = titleConfig
                )
                PocketSpacer(height = 4)
                PocketText(
                    modifier = Modifier,
                    config = contentConfig
                )
                PocketSpacer(height = 10)
                PocketPrimaryButton(
                    modifier = Modifier.align(Alignment.End),
                    config = buttonConfig,
                    primaryColor = buttonBackground,
                    onClick = onBtnClick
                )
            }

        }
    } else {
        onUICalculationError.invoke()
    }

}