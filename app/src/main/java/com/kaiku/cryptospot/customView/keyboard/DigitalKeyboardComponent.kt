package com.kaiku.cryptospot.customView.keyboard

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kaiku.composecomponent.color_484848
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.component.text.AnimatedPocketText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.component.text.PocketTextWithClickableEffect
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.model.rememberClickableConfig
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text16Sp
import com.kaiku.composecomponent.utils.text19Sp
import com.kaiku.cryptospot.customView.icon.SimpleIconButton
import com.kaiku.cryptospot.customView.text.SimpleText
import com.kaiku.cryptospot.customView.text.data.SimpleTextConfig
import com.kaiku.cryptospot.extension.toStyleOfDigitalKeyBoard

/**
 * @sample DigitalKeyboardComponent 關於compose數字鍵盤
 *
 * @param sheetState 需要一個 rememberModalBottomSheetState
 * @param initValue 鍵盤輸入欄位初始值
 * @param hint 當鍵盤輸入欄位為空時，顯示之提示字
 * @param isKeyboardDotEnable 是否可以輸入小數點
 * @param onConfirm 按下確認時的callback
 * @param onDismissRequest 鍵盤收回時的callback
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalKeyboardComponent(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    initValue: String = "",
    hint: String = "",
    isKeyboardDotEnable: Boolean = true,
    onConfirm: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onDismissRequest.invoke()
        }
    ) {
        DigitalKeyboardContent(
            modifier = modifier,
            initValue = initValue,
            hint = hint,
            isKeyboardDotEnable = isKeyboardDotEnable,
            onConfirm = {
                onConfirm.invoke(it)
            }
        )
    }
}

@Composable
private fun DigitalKeyboardContent(
    modifier: Modifier = Modifier,
    initValue: String,
    hint: String,
    isKeyboardDotEnable: Boolean = true,
    onConfirm: (String) -> Unit
) {
    val vm: DigitalKeyboardComponentViewModel = viewModel()

    val digitalStyle = text19Sp(600)

    val appendLambda = remember<(String) -> Unit>(vm)
    {
        {
            vm.appendValue(it)
        }
    }

    val subLambda = remember(vm)
    {
        {
            vm.subValue()
        }
    }

    LaunchedEffect(Unit) {
        vm.clearValue()
        vm.appendValue(initValue)
    }

    val text by vm.textValue.collectAsStateWithLifecycle()

    ConstraintLayout(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 3.sdp())
    ) {

        val (editText) = createRefs()

        val (one, two, three) = createRefs()

        val (four, five, six) = createRefs()

        val (seven, eight, nine) = createRefs()

        val (zero, clear, dot, backPress, confirm) = createRefs()

        val (spacer) = createRefs()

        val line25 = createGuidelineFromStart(0.25f)

        val line50 = createGuidelineFromStart(0.5f)

        val line75 = createGuidelineFromStart(0.75f)

        val pTop = 5.sdp()
        val pStartEnd = 3.sdp()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .constrainAs(editText) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            contentAlignment = Alignment.Center
        ) {
            val animatedTextStyle by animateTextStyleAsState(
                targetValue = if (text.isEmpty()) {
                    text16Sp()
                } else {
                    text19Sp(500)
                },
                animationSpec = tween(durationMillis = 300, easing = LinearEasing)
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 3.sdp(), vertical = 8.sdp())
                    .heightIn(50.sdp()),
                color = color_484848,
                shape = RoundedCornerShape(12.sdp())
            ) {
                val durations = if (text.isEmpty()) {
                    500
                } else {
                    200
                }

                AnimatedPocketText(
                    config = PocketTextConfig(
                        value = text.ifEmpty { hint },
                        textColor = if (text.isEmpty()) {
                            color_9e9e9f
                        } else {
                            Color.White
                        },
                        style = animatedTextStyle
                    ),
                    transitionSpec = {
                        fadeIn(animationSpec = tween(durationMillis = durations)) togetherWith
                                fadeOut(animationSpec = tween(durationMillis = durations))
                    }
                )
            }

        }

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(one) {
                    top.linkTo(editText.bottom, margin = pTop)
                    start.linkTo(parent.start, margin = pStartEnd)
                    end.linkTo(line25, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "1",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(two) {
                    top.linkTo(editText.bottom, margin = pTop)
                    start.linkTo(line25, margin = pStartEnd)
                    end.linkTo(line50, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "2",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(three) {
                    top.linkTo(editText.bottom, margin = pTop)
                    start.linkTo(line50, margin = pStartEnd)
                    end.linkTo(line75, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "3",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        LocalSurfaceWithClickableEffect(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(backPress) {
                    top.linkTo(editText.bottom, margin = pTop)
                    start.linkTo(line75, margin = pStartEnd)
                    end.linkTo(parent.end, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            shape = RoundedCornerShape(12.sdp()),
            color = Color.Gray,
            clickableConfig = rememberClickableConfig(
                needHaptic = true
            ),
            content = {
                Icon(
                    modifier = Modifier
                        .size(30.sdp())
                        .align(Alignment.Center),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            onClick = subLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(four) {
                    top.linkTo(one.bottom, margin = pTop)
                    start.linkTo(parent.start, margin = pStartEnd)
                    end.linkTo(line25, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "4",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(five) {
                    top.linkTo(two.bottom, margin = pTop)
                    start.linkTo(line25, margin = pStartEnd)
                    end.linkTo(line50, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "5",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(six) {
                    top.linkTo(three.bottom, margin = pTop)
                    start.linkTo(line50, margin = pStartEnd)
                    end.linkTo(line75, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "6",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(seven) {
                    top.linkTo(four.bottom, margin = pTop)
                    start.linkTo(parent.start, margin = pStartEnd)
                    end.linkTo(line25, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "7",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(eight) {
                    top.linkTo(five.bottom, margin = pTop)
                    start.linkTo(line25, margin = pStartEnd)
                    end.linkTo(line50, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "8",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(nine) {
                    top.linkTo(six.bottom, margin = pTop)
                    start.linkTo(line50, margin = pStartEnd)
                    end.linkTo(line75, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "9",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(clear) {
                    top.linkTo(seven.bottom, margin = pTop)
                    start.linkTo(parent.start, margin = pStartEnd)
                    end.linkTo(line25, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "清除",
                style = digitalStyle
            ),
            onClick = {
                vm.clearValue()
            }
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(zero) {
                    top.linkTo(eight.bottom, margin = pTop)
                    start.linkTo(line25, margin = pStartEnd)
                    end.linkTo(line50, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "0",
                style = digitalStyle
            ),
            onClick = appendLambda
        )

        DigitalPressButton(
            modifier = Modifier
                .height(50.sdp())
                .constrainAs(dot) {
                    top.linkTo(nine.bottom, margin = pTop)
                    start.linkTo(line50, margin = pStartEnd)
                    end.linkTo(line75, margin = pStartEnd)
                    width = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = ".",
                style = text19Sp(800),
                isEnable = isKeyboardDotEnable
            ),
            color = Color.Gray.copy(
                alpha = if (isKeyboardDotEnable) 1f else 0.5f
            ),
            onClick = {
                if (isKeyboardDotEnable) {
                    appendLambda.invoke(".")
                }
            }
        )

        Spacer(
            modifier = Modifier
                .height(40.sdp())
                .constrainAs(spacer) {
                    top.linkTo(clear.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

        )

        DigitalPressButton(
            modifier = Modifier
                .constrainAs(confirm) {
                    top.linkTo(backPress.bottom, margin = pTop)
                    start.linkTo(line75, margin = pStartEnd)
                    end.linkTo(parent.end, margin = pStartEnd)
                    bottom.linkTo(spacer.top)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            config = PocketTextConfig(
                value = "確認",
                style = digitalStyle
            ),
            onClick = {
                onConfirm.invoke(text)
            }
        )

    }
}

@Composable
private fun DigitalPressButton(
    modifier: Modifier = Modifier,
    config: PocketTextConfig,
    color: Color = Color.Gray,
    onClick: (String) -> Unit
) {
    PocketTextWithClickableEffect(
        modifier = modifier,
        shape = RoundedCornerShape(12.sdp()),
        color = color,
        config = config,
        clickableConfig = rememberClickableConfig(needHaptic = config.isEnable),
        onClick = {
            onClick.invoke(config.value)
        }
    )
}

@Deprecated("v1.3.8要替換")
@Composable
private fun LocalSurfaceWithClickableEffect(
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    color: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = contentColorFor(color),
    tonalElevation: Dp = 0.dp,
    shadowElevation: Dp = 0.dp,
    border: BorderStroke? = null,
    isScaleEnabled: Boolean = true,
    clickableConfig: ClickableConfig = rememberClickableConfig(
        needSound = isScaleEnabled,
        needRipple = isScaleEnabled,
        clickEffect = false
    ),
    content: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit
) {
    var scale by remember { mutableFloatStateOf(1f) }
    Surface(
        modifier = modifier.scale(if (isScaleEnabled) scale else 1f),
        shape = shape,
        color = color,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        border = border,
        content = {
            Box(
                modifier = boxModifier.clickableEffectConfig(
                    config = clickableConfig,
                    onScaling = { scale = it },
                    onClick = onClick
                )
            ) {
                content.invoke(this)
            }
        }
    )
}

@Composable
fun animateTextStyleAsState(
    targetValue: TextStyle,
    animationSpec: AnimationSpec<Float> = spring(),
    finishedListener: ((TextStyle) -> Unit)? = null
): State<TextStyle> {

    val animation = remember { Animatable(0f) }
    var previousTextStyle by remember { mutableStateOf(targetValue) }
    var nextTextStyle by remember { mutableStateOf(targetValue) }

    val textStyleState = remember(animation.value) {
        derivedStateOf {
            lerp(previousTextStyle, nextTextStyle, animation.value)
        }
    }

    LaunchedEffect(targetValue, animationSpec) {
        previousTextStyle = textStyleState.value
        nextTextStyle = targetValue
        animation.snapTo(0f)
        animation.animateTo(1f, animationSpec)
        finishedListener?.invoke(textStyleState.value)
    }

    return textStyleState
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun DigitalKeyboardComponentPreview() {

    // Press start interactive mode to preview

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    DigitalKeyboardComponent(
        initValue = "",
        hint = "5487",
        sheetState = sheetState,
        onConfirm = {},
        onDismissRequest = {}
    )
}
