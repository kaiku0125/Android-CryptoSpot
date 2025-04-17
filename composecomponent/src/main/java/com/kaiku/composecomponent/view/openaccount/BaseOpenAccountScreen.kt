package com.kaiku.composecomponent.view.openaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.component.image.PocketAsyncImage
import com.kaiku.composecomponent.component.image.PocketAsyncImageConfig
import com.kaiku.composecomponent.component.text.PocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickEffect
import com.kaiku.composecomponent.component.topbar.BaseTopBarComponent
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.drawableProvider.DrawableProvider
import com.kaiku.composecomponent.utils.localDrawableProvider
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text15Sp
import com.kaiku.composecomponent.utils.text17Sp
import com.kaiku.composecomponent.view.openaccount.data.OpenAccountClickArea
import com.kaiku.composecomponent.view.openaccount.data.OpenAccountScreenAction
import com.kaiku.composecomponent.view.openaccount.data.OpenAccountViewState

@Composable
fun BaseOpenAccountScreen(
    modifier: Modifier = Modifier,
    drawableProvider: DrawableProvider = localDrawableProvider(),
    viewState: OpenAccountViewState,
    action: (OpenAccountScreenAction) -> Unit
) {
    Scaffold(
        topBar = {
            BaseTopBarComponent(
                modifier = Modifier.height(44.sdp()),
                textConfig = PocketTextConfig(
                    value = viewState.title,
                    style = text17Sp(500),
                    textColor = Color.White
                ),
                navContent = {
                    IconButton(
                        modifier = Modifier.fillMaxHeight(),
                        onClick = {
                            action.invoke(
                                OpenAccountScreenAction.CloseClick
                            )
                        },
                        content = {
                            Icon(
                                modifier = Modifier.size(24.sdp()),
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onBackground,
                            )
                        }
                    )
                },
                background = color_333333
            )
        },
    ) { scaffoldPadding ->
        Column(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(scaffoldPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                val (image, btnField) = createRefs()

                PocketAsyncImage(
                    modifier = Modifier.constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    imageConfig = PocketAsyncImageConfig(
                        url = viewState.openAccountMainPicUrl,
                        errorDrawable = drawableProvider.openAccountMain,
                        shape = RoundedCornerShape(0.dp),
                        description = "open_account_image",
                    ),
                    clickConfig = ClickableConfig(
                        needRipple = false,
                        needSound = false,
                        needHaptic = false
                    )
                )

                ButtonField(
                    modifier = Modifier.constrainAs(btnField) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(image.bottom)
                    },
                    drawableProvider = drawableProvider,
                    viewState = viewState,
                    action = action
                )
            }

            Spacer(modifier = Modifier.height(10.sdp()))

            TextUrlField(
                viewState = viewState,
                action = action
            )

            Spacer(modifier = Modifier.height(30.sdp()))
        }
    }
}

@Composable
private fun ButtonField(
    modifier: Modifier = Modifier,
    drawableProvider: DrawableProvider,
    viewState: OpenAccountViewState,
    action: (OpenAccountScreenAction) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(44.sdp()))

        PocketAsyncImage(
            imageConfig = PocketAsyncImageConfig(
                url = viewState.openAccountTopButtonUrl,
                ratio = 247f / 48f,
                errorDrawable = drawableProvider.openAccountTopBtn
            ),
            clickConfig = ClickableConfig(
                needRipple = false
            ),
            onClick = {
                action.invoke(
                    OpenAccountScreenAction.OnEventClick(
                        area = OpenAccountClickArea.TOP_BUTTON,
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(44.sdp()))


        PocketAsyncImage(
            imageConfig = PocketAsyncImageConfig(
                url = viewState.openAccountBottomButtonUrl,
                ratio = 247f / 48f,
                errorDrawable = drawableProvider.openAccountBottomBtn
            ),
            clickConfig = ClickableConfig(
                needRipple = false
            ),
            onClick = {
                action.invoke(
                    OpenAccountScreenAction.OnEventClick(
                        area = OpenAccountClickArea.BOTTOM_BUTTON,
                    )
                )
            }
        )
    }

}

@Composable
private fun TextUrlField(
    modifier: Modifier = Modifier,
    viewState: OpenAccountViewState,
    action: (OpenAccountScreenAction) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PocketTextWithClickEffect(
            config = PocketTextConfig(
                value = viewState.leftText,
                style = text15Sp(),
                textColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                action.invoke(
                    OpenAccountScreenAction.OnEventClick(
                        area = OpenAccountClickArea.LEFT_TEXT,
                    )
                )
            }
        )
        Spacer(modifier = Modifier.width(10.dp))
        PocketText(
            config = PocketTextConfig(
                value = "|",
                style = text15Sp(),
                textColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        PocketTextWithClickEffect(
            config = PocketTextConfig(
                value = viewState.midText,
                style = text15Sp(),
                textColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                action.invoke(
                    OpenAccountScreenAction.OnEventClick(
                        area = OpenAccountClickArea.MID_TEXT,
                    )
                )
            }
        )
        Spacer(modifier = Modifier.width(10.dp))
        PocketText(
            config = PocketTextConfig(
                value = "|",
                style = text15Sp(),
                textColor = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(modifier = Modifier.width(10.dp))
        PocketTextWithClickEffect(
            config = PocketTextConfig(
                value = viewState.rightText,
                style = text15Sp(),
                textColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                action.invoke(
                    OpenAccountScreenAction.OnEventClick(
                        area = OpenAccountClickArea.RIGHT_TEXT,
                    )
                )
            }
        )

    }
}
