package com.kaiku.cryptospot.customView.dialog

import android.graphics.Color.parseColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.kaiku.cryptospot.R
import com.kaiku.cryptospot.customView.text.TextWithIcon
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EdmDialog(
    onNotRemind: () -> Unit,
    onDismiss: () -> Unit
) {
    var screenWidth by remember { mutableStateOf(0) }
    var screenHeight by remember { mutableStateOf(0) }

    var mainWidth by remember { mutableStateOf(0) }
    var mainHeight by remember { mutableStateOf(0) }

    var widthRatio by remember { mutableStateOf(1f) }
    var heightRatio by remember { mutableStateOf(1f) }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {}
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta)
                .onGloballyPositioned {
                    screenWidth = it.size.width
                    screenHeight = it.size.height

                    Timber.e("外框width = ${screenWidth}, 外框height = ${screenHeight}")

                }
        ) {

            val (cancel, mainBox, mainView, checkView) = createRefs()

            Box(
                modifier = Modifier
                    .aspectRatio(398f / 604f) // 398 604,
                    .constrainAs(mainBox) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .onGloballyPositioned {
                        mainWidth = it.size.width
                        mainHeight = it.size.height

                        Timber.e("主體width = $mainWidth, 主體height = $mainHeight")

                        widthRatio = screenWidth / mainWidth.toFloat()
                        heightRatio = screenHeight / mainHeight.toFloat()
                        Timber.e("倍率width = ${widthRatio}, 倍率height = ${heightRatio}")
                    }
                    .background(Color.Yellow)
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.mipmap.bg_edm),
                    contentDescription = null
                )

                ConstraintLayout(
                    modifier = Modifier
                        .width(mainWidth.dp)
                        .height(mainHeight.dp)
//                        .background(Color.Green)
                ) {
                    val startGuide = createGuidelineFromStart(0.17f)

                    val endGuide = createGuidelineFromEnd(0.15f)

                    val topGuide = createGuidelineFromTop(0.2f)

                    val item1Top = createGuidelineFromTop(0.28f)
                    val item1Bottom = createGuidelineFromTop(0.5f)

                    val item2Top = createGuidelineFromTop(0.5f)
                    val item2Bottom = createGuidelineFromTop(0.7f)

                    val item3Top = createGuidelineFromTop(0.7f)
                    val item3Bottom = createGuidelineFromTop(0.9f)

                    val (titleText, item1, item2, item3) = createRefs()

                    TitleText(
                        modifier = Modifier.constrainAs(titleText) {
                            top.linkTo(topGuide)
                            start.linkTo(startGuide)
                            end.linkTo(endGuide)
                        }
                    )

                    EdmItemComponent(
                        modifier = Modifier.constrainAs(item1){
                            top.linkTo(item1Top)
                            start.linkTo(startGuide)
                            end.linkTo(endGuide)
                            bottom.linkTo(item1Bottom)
                            height = Dimension.fillToConstraints
                        },
                        needCloud = true,
                        needGift = false
                    )

                    EdmItemComponent(
                        modifier = Modifier.constrainAs(item2){
                            top.linkTo(item2Top)
                            start.linkTo(startGuide)
                            end.linkTo(endGuide)
                            bottom.linkTo(item2Bottom)
                        },
                        needCloud = false,
                        needGift = true
                    )

                    EdmItemComponent(
                        modifier = Modifier.constrainAs(item3){
                            top.linkTo(item3Top)
                            start.linkTo(startGuide)
                            end.linkTo(endGuide)
                            bottom.linkTo(item3Bottom)
                        },
                        needCloud = false,
                        needGift = false
                    )

                }

            }

            IconButton(
                modifier = Modifier.constrainAs(cancel) {
                    top.linkTo(mainBox.top)
                    end.linkTo(parent.end)
                },
                onClick = { onDismiss.invoke() }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Row(
                modifier = Modifier
                    .constrainAs(checkView) {
                        top.linkTo(mainBox.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isChecked = remember { mutableStateOf(false) }

                Checkbox(
                    checked = isChecked.value,
                    onCheckedChange = {
                        isChecked.value = it
                        if (it) {
                            onNotRemind.invoke()
                        }
                    }
                )
                Text(text = "今日不再顯示")
            }

        }


    }
}

@Preview
@Composable
private fun TitleText(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(207.dp)
            .height(46.dp)
            .background(
                color = Color.White,
                shape = MaterialTheme.shapes.extraLarge
            )
            .border(
                width = 2.dp,
                color = Color(0xFF1F378D),
                shape = MaterialTheme.shapes.extraLarge
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .background(
                    color = Color(parseColor("#D8E1FF")),
                    shape = MaterialTheme.shapes.extraLarge
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "5月活動通知",
                style = TextStyle(
                    fontSize = 21.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF1F378D),
                )
            )
        }
    }
}


@Preview
@Composable
private fun EdmItemTitle(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(27.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 100.dp)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF3450B2),
                shape = RoundedCornerShape(size = 100.dp)
            ),
        contentAlignment = Alignment.Center
    ) {

        Row(
            modifier = Modifier.padding(start = 5.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(id = R.drawable.ic_congratulation),
                contentDescription = null
            )
            Text(
                text = "開戶活動",
                style = TextStyle(
                    fontSize = 13.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF3450B2),
                )
            )
        }
    }

}

@Preview
@Composable
private fun EdmItemMain(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = Color(0xFF557AFF),
                shape = RoundedCornerShape(size = 15.dp)
            )
            .padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 20.dp)
            .border(
                width = 1.5.dp,
                color = Color(0xFF3450B2),
                shape = RoundedCornerShape(size = 15.dp)
            )
    ) {

        Row(
            modifier = Modifier
                .background(
                    color = Color.Cyan,
                    shape = RoundedCornerShape(size = 15.dp)
                )
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "抽抽抽 &8000 君悅年菜組！",
                maxLines = 2,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                )
            )

            Box(
                modifier = Modifier
                    .width(58.dp)
                    .height(26.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .clickable {

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "詳情",
                    fontSize = 11.sp,
                    color = Color(0xFF3450B2)
                )
            }
        }
    }
}

@Preview
@Composable
fun EdmItemComponent(
    modifier: Modifier = Modifier,
    needCloud: Boolean = true,
    needGift: Boolean = true
) {
    ConstraintLayout(
        modifier = modifier.fillMaxWidth(),
    ) {
        val (main, title, cloud, gift) = createRefs()

        val startGuide = createGuidelineFromStart(0.17f)

        val endGuide = createGuidelineFromEnd(0.15f)

        val cloudEnd = createGuidelineFromEnd(0.08f)

        EdmItemMain(
            modifier = Modifier.constrainAs(main) {
                top.linkTo(parent.top, margin = 14.dp)
                start.linkTo(startGuide)
                end.linkTo(endGuide)
                bottom.linkTo(parent.bottom, margin = 14.dp)
            }
        )

        EdmItemTitle(
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(main.top)
                    start.linkTo(main.start, margin = 20.dp)
                    bottom.linkTo(main.top)
                }
        )

        if (needCloud) {
            Image(
                modifier = Modifier
                    .width(50.dp)
                    .height(39.dp)
                    .constrainAs(cloud) {
                        top.linkTo(main.top)
                        end.linkTo(cloudEnd)
                        bottom.linkTo(main.top)
                    },
                painter = painterResource(id = R.drawable.ic_cloud),
                contentDescription = null
            )
        }

        if (needGift) {
            Image(
                modifier = Modifier
                    .width(35.dp)
                    .height(32.dp)
                    .constrainAs(gift) {
                        bottom.linkTo(main.bottom)
                        top.linkTo(main.bottom, margin = 5.dp)
                        end.linkTo(main.end)
                    },
                painter = painterResource(id = R.drawable.ic_gift),
                contentDescription = null
            )
        }


    }
}