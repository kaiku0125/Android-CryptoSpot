package com.kaiku.composecomponent.view.mission.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.kaiku.composecomponent.R
import com.kaiku.composecomponent.color_333333
import com.kaiku.composecomponent.color_9e9e9f
import com.kaiku.composecomponent.color_gift_bg_tw
import com.kaiku.composecomponent.color_gift_bg_us
import com.kaiku.composecomponent.component.image.PocketAsyncImage
import com.kaiku.composecomponent.component.image.PocketAsyncImageConfig
import com.kaiku.composecomponent.component.loading.HorizontalProgressComponent
import com.kaiku.composecomponent.component.spacer.PocketSpacer
import com.kaiku.composecomponent.component.text.AnimatedPocketAutoSizeText
import com.kaiku.composecomponent.component.text.PocketTextConfig
import com.kaiku.composecomponent.extension.advanceShadow
import com.kaiku.composecomponent.extension.circleShadow
import com.kaiku.composecomponent.extension.clickableEffectConfig
import com.kaiku.composecomponent.extension.toStepMissionText
import com.kaiku.composecomponent.model.ClickableConfig
import com.kaiku.composecomponent.theme_color_primary_tw
import com.kaiku.composecomponent.theme_color_primary_us
import com.kaiku.composecomponent.utils.sdp
import com.kaiku.composecomponent.utils.text11Sp
import com.kaiku.composecomponent.view.mission.data.RewardStatus
import kotlinx.coroutines.delay

@Composable
private fun getIconSize(): Dp = 32.sdp()

/**
 * 進度條元件(有不同比例的進度條)
 *
 * @property mascotUrl mascot的圖片
 * @property errorMascotRes url錯誤時mascot的圖片
 * @property stepList 階段列表
 * @property receivedStatusList 此階段獎勵是否被領過列表
 * @property currentStep 現在進度
 * @property animationSpec 動畫設定
 * @property onClick export 點擊未達成的獎勵
 */
@Composable
internal fun DiffRatioHorizontalProgress(
    modifier: Modifier = Modifier,
    isTw: Boolean,
    mascotUrl: String = "",
    @DrawableRes errorMascotRes: Int = R.drawable.pocket_ic_mission_mascot_cow,
    stepList: List<Int> = listOf(0, 100, 300, 500, 600, 1000),
    receivedStatusList: List<RewardStatus>,
    currentStep: Int = 0,
    animationSpec: AnimationSpec<Float> = tween(700),
    onClick: (String) -> Unit,
) {
    val density = LocalDensity.current

    val halfIconSize = getIconSize() / 2

    // 整個layout的寬度
    var totalWidth by remember { mutableIntStateOf(0) }

    // 進度條百分比
    var intervalProgress by remember { mutableFloatStateOf(0f) }

    // mascot相關
    var mascotProgress by remember { mutableFloatStateOf(0f) } // 寵物在該區間進度
    var isMascotVisible by remember { mutableStateOf(true) } // 是否顯示寵物
    var mascotOffset by remember { mutableIntStateOf(0) } // 寵物偏移dp值

    // 解決吉祥物左側progressbar突出問題
    val progressbarPadding by remember {
        derivedStateOf {
            with(density) {
                if (!isMascotVisible || mascotOffset.toDp() > halfIconSize) {
                    0.dp
                } else {
                    5.dp
                }
            }
        }
    }

    val intervalProgressAnimation = animateFloatAsState(
        targetValue = intervalProgress,
        label = "intervalProgressAnimation",
        animationSpec = animationSpec
    ).value

    val mascotOffsetAnimation = animateIntAsState(
        targetValue = mascotOffset,
        label = "mascotOffsetAnimation",
        animationSpec = tween(1000)
    ).value

    LaunchedEffect(currentStep) {
        val result = diffRatioProgressCalculation(
            currentStep = currentStep,
            stepList = stepList
        )
        intervalProgress = result.first
        isMascotVisible = result.second
        mascotProgress = result.third
    }

    LaunchedEffect(totalWidth, mascotProgress) {
        if (totalWidth != 0 && mascotProgress != 0f) {
            mascotOffset = (totalWidth.toFloat() * mascotProgress).toInt()
        }
    }

    ConstraintLayout(
        modifier = modifier.onGloballyPositioned {
            totalWidth = it.size.width
        }
    ) {
        val (progress, gift, mascot) = createRefs()
        val marginTop = 11.sdp()
        HorizontalProgressComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = progressbarPadding.value
                        .toInt()
                        .sdp()
                )
                .height(10.sdp())
                .constrainAs(progress) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = marginTop)
                },
            percentage = intervalProgress,
            progressColor = if (isTw) {
                theme_color_primary_tw
            } else {
                theme_color_primary_us
            },
            hoverContent = {}
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .constrainAs(gift) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stepList.forEachIndexed { index, step ->
                val ratio = 1 / (stepList.size - 1).toFloat() * index
                if (index == 0) {
                    EmptyIconItem(
                        step = step.toString()
                    )
                } else {
                    val doesReach = intervalProgressAnimation >= ratio

                    StepIconItem(
                        step = step,
                        isTw = isTw,
                        doesReach = doesReach,
                        status = receivedStatusList[index],
                        onClick = {
                            onClick.invoke(step.toString())
                        }
                    )
                }
            }
        }
        if (isMascotVisible) {
            PocketAsyncImage(
                modifier = Modifier
                    .size(getIconSize())
                    .offset(
                        x = with(density) {
                            // 16.dp 是圖片的一半寬度，因此若算出來的offset並沒有超過16.dp，我們就不一動mascot
                            val offset = mascotOffsetAnimation.toDp()
                            if (offset <= halfIconSize) {
                                0.dp
                            } else {
                                (offset - halfIconSize)
                            }
                        }
                    )
                    .constrainAs(mascot) {
                        start.linkTo(parent.start)
                    },
                imageConfig = PocketAsyncImageConfig(
                    url = mascotUrl,
                    errorDrawable = errorMascotRes
                ),
                clickConfig = ClickableConfig(
                    needRipple = false,
                    needSound = false
                )
            )
        }
    }
}

//先寫兩種UI版本再做比較
@Composable
internal fun AdjustDiffRatioHorizontalProgress(
    modifier: Modifier = Modifier,
    isTw: Boolean,
    mascotUrl: String = "",
    @DrawableRes errorMascotRes: Int = R.drawable.pocket_ic_mission_mascot_cow,
    stepList: List<Int> = listOf(0, 100, 300, 500, 600, 1000),
    receivedStatusList: List<RewardStatus>,
    currentStep: Int = 0,
    animationSpec: AnimationSpec<Float> = tween(700),
    onClick: (String) -> Unit
) {
    val density = LocalDensity.current

    val halfIconSize = getIconSize() / 2

    // 整個layout的寬度
    var totalWidth by remember { mutableIntStateOf(0) }

    // 進度條百分比
    var intervalProgress by remember { mutableFloatStateOf(0f) }

    // mascot相關
    var mascotProgress by remember { mutableFloatStateOf(0f) } // 寵物在該區間進度
    var isMascotVisible by remember { mutableStateOf(true) } // 是否顯示寵物
    var mascotOffset by remember { mutableIntStateOf(0) } // 寵物偏移dp值

    // 解決吉祥物左側progressbar突出問題
    val progressbarPadding by remember {
        derivedStateOf {
            with(density) {
                if (!isMascotVisible || mascotOffset.toDp() > halfIconSize) {
                    0.dp
                } else {
                    5.dp
                }
            }
        }
    }

    val intervalProgressAnimation = animateFloatAsState(
        targetValue = intervalProgress,
        label = "intervalProgressAnimation",
        animationSpec = animationSpec
    ).value

    val mascotOffsetAnimation = animateIntAsState(
        targetValue = mascotOffset,
        label = "mascotOffsetAnimation",
        animationSpec = tween(1000)
    ).value

    LaunchedEffect(currentStep) {
        val result = diffRatioProgressCalculation(
            currentStep = currentStep,
            stepList = stepList
        )
        intervalProgress = result.first
        isMascotVisible = result.second
        mascotProgress = result.third
    }

    // 算出寵物偏移量
    LaunchedEffect(totalWidth, mascotProgress) {
        if (totalWidth != 0 && mascotProgress != 0f) {
            mascotOffset = (totalWidth.toFloat() * mascotProgress).toInt()
        }
    }

    ConstraintLayout(
        modifier = modifier.onGloballyPositioned {
            with(density) {
                totalWidth = it.size.width
            }
        }
    ) {
        val (progress, gift, gift2, mascot) = createRefs()
        val marginTop = 11.sdp()
        HorizontalProgressComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = progressbarPadding.value
                        .toInt()
                        .sdp()
                )
                .height(10.dp)
                .constrainAs(progress) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top, margin = marginTop)
                },
            percentage = intervalProgress,
            hoverContent = {}
        )

        // 因為figma的設計是進度條的兩端都有icon，且並沒有按照比例去擺，所以這邊要特別處理
        // 每一個item的寬度hard coded為32.dp
        // 先擺放中間的item(為了要等比例長度，所以要兩邊要先擺放一半的空item)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .constrainAs(gift) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stepList.forEachIndexed { index, step ->
                val ratio = 1 / (stepList.size - 1).toFloat() * index
                when (index) {
                    0, stepList.lastIndex -> {
                        EmptyIcon(size = halfIconSize)
                    }

                    else -> {
                        StepIconItem(
                            step = step,
                            isTw = isTw,
                            doesReach = intervalProgressAnimation >= ratio,
                            status = receivedStatusList[index],
                            onClick = {
                                onClick.invoke(it.toString())
                            }
                        )
                    }
                }
            }
        }
        // 再擺上前後的item(為了要等比例長度，所以中間要擺放32.dp的空item，或是可以不擺)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .constrainAs(gift2) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            stepList.forEachIndexed { index, step ->
                val ratio = 1 / (stepList.size - 1).toFloat() * index
                when (index) {
                    0 -> {
                        EmptyIconItem(
                            step = step.toString()
                        )
                    }

                    stepList.lastIndex -> {
                        val doesReach = intervalProgressAnimation >= ratio
                        StepIconItem(
                            step = step,
                            isTw = isTw,
                            doesReach = doesReach,
                            status = receivedStatusList[index],
                            onClick = {
                                onClick.invoke(it.toString())
                            }
                        )
                    }

                    else -> Unit
                }
            }
        }
        if (isMascotVisible) {
            PocketAsyncImage(
                modifier = Modifier
                    .size(getIconSize())
                    .offset(
                        x = with(density) {
                            // 16.dp 是圖片的一半寬度，因此若算出來的offset並沒有超過16.dp，我們就不一動mascot
                            val offset = mascotOffsetAnimation.toDp()
                            if (offset <= halfIconSize) {
                                0.dp
                            } else {
                                (offset - halfIconSize)
                            }
                        }
                    )
                    .constrainAs(mascot) {
                        start.linkTo(parent.start)
                    },
                imageConfig = PocketAsyncImageConfig(
                    url = mascotUrl,
                    errorDrawable = errorMascotRes
                ),
                clickConfig = ClickableConfig(
                    needRipple = false,
                    needSound = false
                )
            )
        }
    }
}


@Composable
private fun EmptyIcon(
    modifier: Modifier = Modifier,
    size: Dp = getIconSize()
) {
    Box(
        modifier = modifier
            .size(size)
            .background(Color.Transparent)
    )
}

@Composable
private fun RowScope.EmptyIconItem(
    modifier: Modifier = Modifier,
    step: String
) {
    Column(
        modifier = modifier.width(getIconSize())
    ) {
        EmptyIcon()
        PocketSpacer(height = 4)
        AnimatedPocketAutoSizeText(
            modifier = Modifier.padding(start = 3.sdp()),
            config = PocketTextConfig(
                value = step,
                style = text11Sp(),
                maxLines = 1
            )
        )
    }
}

@Composable
private fun StepIcon(
    modifier: Modifier = Modifier,
    isTw: Boolean,
    doesReach: Boolean,
    onClick: () -> Unit
) {
    val imageId = if (doesReach) {
        R.drawable.pocket_ic_mission_gift
    } else {
        R.drawable.pocket_ic_mission_gift_gray
    }

    val imageBg = if (doesReach) {
        if (isTw) {
            color_gift_bg_tw
        } else {
            color_gift_bg_us
        }
    } else {
        color_9e9e9f
    }

    var rotateAnimationAngle by remember { mutableFloatStateOf(0f) }

    val rotateAnimation = animateFloatAsState(
        targetValue = rotateAnimationAngle,
        animationSpec = tween(
            easing = LinearEasing
        ), label = ""
    )

    Box(
        modifier = modifier
            .size(getIconSize())
            .circleShadow(
                blurRadius = 6.sdp(),
                centerOffsetY = 6.sdp()
            )
            .clip(CircleShape)
            .background(imageBg)
            .clickableEffectConfig(
                onClick = {
                    onClick.invoke()
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .size(22.sdp())
                .graphicsLayer {
                    rotationZ = rotateAnimation.value
                },
            painter = painterResource(id = imageId),
            contentDescription = "gift"
        )
    }

    LaunchedEffect(imageId) {
        if (imageId == R.drawable.pocket_ic_mission_gift) {
            repeat(20) {
                rotateAnimationAngle = -25f
                delay(100)
                rotateAnimationAngle = 25f
                delay(100)
            }
            rotateAnimationAngle = 0f
        }
    }
}

@Composable
private fun RowScope.StepIconItem(
    modifier: Modifier = Modifier,
    step: Int,
    isTw: Boolean,
    doesReach: Boolean,
    status: RewardStatus,
    onClick: (Int) -> Unit = {}
) {

    Column(
        modifier = modifier.width(getIconSize()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (status) {
            RewardStatus.HAVE_RECEIVED -> {
                StepReceivedIcon(isTw = isTw)
            }

            RewardStatus.UNRECEIVED -> {
                StepIcon(
                    isTw = isTw,
                    doesReach = doesReach,
                    onClick = {
                        onClick.invoke(step)
                    }
                )
            }

            else -> {
                StepIcon(
                    isTw = isTw,
                    doesReach = false,
                    onClick = {
                        onClick.invoke(step)
                    }
                )
            }
        }
        PocketSpacer(height = 4)
        AnimatedPocketAutoSizeText(
            modifier = Modifier.fillMaxHeight(),
            config = PocketTextConfig(
                value = step.toStepMissionText(),
                style = text11Sp(),
                maxLines = 1
            )
        )
    }
}

@Composable
private fun StepReceivedIcon(
    modifier: Modifier = Modifier,
    isTw: Boolean,
) {
    Box(
        modifier = modifier
            .size(getIconSize())
            .circleShadow(
                blurRadius = 6.sdp(),
                centerOffsetY = 6.sdp()
            )
            .clip(CircleShape)
            .background(
                if (isTw) {
                    theme_color_primary_tw
                } else {
                    theme_color_primary_us
                }
            )
            .border(1.sdp(), color = Color.White, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(24.sdp()),
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.White
        )
    }
}


private fun findStepInterval(
    stepList: List<Int>,
    currentStep: Int
): Pair<Int, Int>? {
    for (i in 0 until stepList.size - 1) {
        val start = stepList[i]
        val end = stepList[i + 1]

        if (currentStep in start..end) {
            return Pair(start, end)
        }
    }
    return null
}

// 回傳(intervalProgress(總進度), isMascotVisible(是否檢視寵物), mascotProgress(寵物在該區間進度))
private fun diffRatioProgressCalculation(
    currentStep: Int,
    stepList: List<Int>
): Triple<Float, Boolean, Float> {
    if (currentStep >= stepList.last()) {
        // 若進度已超出區間上限，則直接設為100%，並隱藏寵物
        return Triple(1f, false, 0f)
    }

    val size = stepList.size
    val stepRatio = 1 / (size - 1).toFloat() // 每個區間佔多少％
    val interval = findStepInterval(stepList, currentStep) // 找出目前所在的區間

    // 總共走了幾個區間
    val nowStep = stepList.indexOfFirst {
        it >= currentStep
    }

    val totalStep = if (nowStep == -1) {
        0
    } else {
        nowStep - 1
    }

    val currentRatioInStep = interval?.let { pair ->
        // 目前所在區間的比例 (ex. 400 的話找出介於300 ~ 500 的比例)
        (currentStep - pair.first) / (pair.second - pair.first).toFloat()
    } ?: 0f

    val currentRatioToAll = currentRatioInStep * stepRatio

    var outputMascotProgress = 0f
    var outputIsMascotVisible = false

    // 算出mascot的位置
    if (nowStep <= 1 && currentStep != stepList[1]) {
        outputIsMascotVisible = true
        outputMascotProgress = currentRatioToAll
    }

    //算出整個進度條的百分比
    val outputIntervalProgress = stepRatio * totalStep + currentRatioToAll

    return Triple(outputIntervalProgress, outputIsMascotVisible, outputMascotProgress)
}


// ------------------------------ Preview ------------------------------
@Preview
@Composable
private fun DiffRatioHorizontalProgressPreview() {

    val stepList = listOf(
        0,
        10000,
        30000,
        50000,
        60000,
        987654321
    )
    val currentStep = 4000
    val currentStep2 = 40000

    val receivedStatusList = listOf(
        RewardStatus.UNKNOWN,
        RewardStatus.HAVE_RECEIVED,
        RewardStatus.UNRECEIVED,
        RewardStatus.CANT_RECEIVE,
        RewardStatus.CANT_RECEIVE,
        RewardStatus.CANT_RECEIVE
    )

    Column {
        DiffRatioHorizontalProgress(
            modifier = Modifier.background(color_333333),
            isTw = true,
            stepList = stepList,
            receivedStatusList = receivedStatusList,
            currentStep = currentStep2,
            onClick = {}
        )

        AdjustDiffRatioHorizontalProgress(
            modifier = Modifier.background(color_333333),
            isTw = true,
            stepList = stepList,
            currentStep = currentStep2,
            receivedStatusList = receivedStatusList,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun ShadowPreview() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
    ) {
        Row {
            StepReceivedIcon(
                modifier = Modifier.advanceShadow(),
                isTw = true
            )
            Spacer(modifier = Modifier.width(8.dp))
            StepReceivedIcon(
                modifier = Modifier.circleShadow(
                    blurRadius = 6.dp,
                    centerOffsetY = 6.dp
                ),
                isTw = false
            )
        }
    }
}
